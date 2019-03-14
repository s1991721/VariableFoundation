package com.jef.variablefoundation.net.download;

import android.text.TextUtils;

import com.jef.variablefoundation.base.BaseApplication;
import com.jef.variablefoundation.db.cache.SettingManager;
import com.jef.variablefoundation.net.RequestProxy;
import com.jef.variablefoundation.utils.async.AsyncTaskVF;
import com.jef.variablefoundation.utils.json.JsonHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;

/**
 * Created by mr.lin on 2018/11/29
 * 下载任务
 */
public class DownloadTask extends AsyncTaskVF<File> {

    private DownloadListener outDownloadListener;
    private String remoteUrl;
    private String directory;
    private String fileName;

    private int threadCount;

    private int[] totalProgress;

    private LinkedList<SubDownloadTask> subDownloadTasks;
    private SubDownloadTask.SubDownloadListener inDownloadListener = new SubDownloadTask.SubDownloadListener() {
        @Override
        public void onProgress(int index, int progress) {
            if (totalProgress[index] == progress) {
                return;
            }

            totalProgress[index] = progress;

            int sum = 0;
            for (int i = 0; i < threadCount; i++) {
                sum += totalProgress[i];
            }

            outDownloadListener.onProgress(sum / threadCount);
        }

        @Override
        public void onSuccess(int index) {
            doneSignal.countDown();
        }

        @Override
        public void onFailed(int index, String reason) {
            outDownloadListener.onFailed(reason);
        }
    };
    private CountDownLatch doneSignal;

    public DownloadTask(DownloadListener downloadListener, String remoteUrl, String directory, String fileName) {
        this(downloadListener, remoteUrl, directory, fileName, 1);
    }

    public DownloadTask(DownloadListener downloadListener, String remoteUrl, String directory, String fileName, int threadCount) {
        this.outDownloadListener = downloadListener;
        this.remoteUrl = remoteUrl;
        this.directory = directory;
        this.fileName = fileName;
        this.threadCount = threadCount;
        subDownloadTasks = new LinkedList<>();
        doneSignal = new CountDownLatch(threadCount);
        totalProgress = new int[threadCount];
    }

    @Override
    public File runOnBackGround() {
        File folder = new File(directory);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        File file = new File(folder, fileName);
        Parameter parameter;

        parameter = loadParameter(remoteUrl);
        if (parameter == null) {
            outDownloadListener.onFailed("Url wrong");
            return null;
        }
        for (int i = 0; i < parameter.threadCount; i++) {
            SubDownloadTask subDownloadTask = new SubDownloadTask(inDownloadListener, parameter, i);
            subDownloadTask.execute();
            subDownloadTasks.add(subDownloadTask);
        }
        try {
            doneSignal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            outDownloadListener.onFailed(e.getMessage());
        }
        return file;
    }

    //加载断点续传参数
    private Parameter loadParameter(String remoteUrl) {
        Parameter parameter = readParameter(remoteUrl);
        if (parameter == null) {
            long length = getFileLength();
            if (length == 0) {
                return null;
            }
            parameter = new Parameter();
            parameter.directory = directory;
            parameter.fileName = fileName;
            parameter.remoteUrl = remoteUrl;
            parameter.totalLength = length;
            parameter.threadCount = threadCount;

            long block = length / threadCount;//每条线程计划负责的长度
            long[] threadLength = new long[threadCount];
            long[] startPosition = new long[threadCount];
            long[] endPosition = new long[threadCount];
            long[] currentPosition = new long[threadCount];
            for (int i = 0; i < threadCount; i++) {
                if (length - block > 0) {//每线程实际负责的长度
                    threadLength[i] = block;
                    endPosition[i] = (i + 1) * block - 1;//每线程结束的位置
                    length -= block;
                } else {
                    threadLength[i] = length;
                    endPosition[i] = parameter.totalLength;
                }

                startPosition[i] = i * block;//每线程开始的位置

                currentPosition[i] = startPosition[i];//每线程已下载的位置
            }

            parameter.threadLength = threadLength;
            parameter.startPosition = startPosition;
            parameter.endPosition = endPosition;
            parameter.currentPosition = currentPosition;
        }
        return parameter;
    }

    //断点续传参数
    private Parameter readParameter(String remoteUrl) {//从应用设置中读取下载参数
        SettingManager settingManager = BaseApplication.getInstance().getManager(SettingManager.class);
        String json = settingManager.getGlobalSetting(remoteUrl, "");
        Parameter parameter;
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        try {
            parameter = JsonHelper.toObject(new JSONObject(json), Parameter.class);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return parameter;
    }

    private void deleteParameter(String remoteUrl) {
        SettingManager settingManager = BaseApplication.getInstance().getManager(SettingManager.class);
        settingManager.removeGlobalSetting(remoteUrl);
    }

    //获取下载文件的总长
    private long getFileLength() {
        return new RequestProxy(null).getFileLength(remoteUrl);
    }

    @Override
    public void runOnUIThread(File file) {
        if (file != null) {
            outDownloadListener.onSuccess();
        }
    }

    public void start() {
        this.execute();
    }

    public void cancel() {
        if (subDownloadTasks != null) {
            for (SubDownloadTask subTask : subDownloadTasks) {
                subTask.cancel();
            }
        }

        deleteParameter(remoteUrl);
        deleteFile();

        outDownloadListener.onCanceled();
    }

    private void deleteFile() {
        File file = new File(directory + File.separator + fileName);
        if (file.exists()) {
            file.delete();
        }
    }

    public void pause() {
        if (subDownloadTasks != null) {
            for (SubDownloadTask subTask : subDownloadTasks) {
                subTask.pause();
            }
        }
        outDownloadListener.onPaused();
    }

    public void reStart() {
        subDownloadTasks.clear();
        doneSignal = new CountDownLatch(threadCount);
        execute();
    }
}
