package com.jef.variablefoundation.net.download;

import com.jef.variablefoundation.utils.async.AsyncTaskVF;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by mr.lin on 2018/11/30
 * 子下载任务
 */
public class SubDownloadTask extends AsyncTaskVF<Integer> {

    private static final int SUCCESS = 0;
    private static final int FAIL = -1;

    private SubDownloadListener subDownloadListener;
    private Parameter parameter;
    private int index;

    private boolean isCanceled = false;
    private boolean isPaused = false;

    public SubDownloadTask(SubDownloadListener subDownloadListener, Parameter parameter, int index) {
        this.subDownloadListener = subDownloadListener;
        this.parameter = parameter;
        this.index = index;
    }

    @Override
    public Integer runOnBackGround() {
        File file = new File(parameter.directory + File.separator + parameter.fileName);
        InputStream inputStream = null;
        RandomAccessFile savedFile = null;
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .addHeader("RANGE", String.format("bytes=%s-%s", parameter.currentPosition[index], parameter.endPosition[index]))
                .url(parameter.remoteUrl)
                .build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response != null) {
                inputStream = response.body().byteStream();
                savedFile = new RandomAccessFile(file, "rw");
                savedFile.seek(parameter.currentPosition[index]);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = inputStream.read(buffer)) != -1) {
                    if (isCanceled) {
                        return FAIL;
                    }
                    if (isPaused) {
                        return FAIL;
                    }
                    savedFile.write(buffer, 0, len);
                    parameter.currentPosition[index] += len;
                    int progress = (int) ((parameter.currentPosition[index] - parameter.startPosition[index]) * 100 / parameter.threadLength[index]);
                    publishProgress(progress);
                }
                return SUCCESS;
            } else {
                subDownloadListener.onFailed(index, "网络错误");
            }
        } catch (IOException e) {
            e.printStackTrace();
            subDownloadListener.onFailed(index, e.getMessage());
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (savedFile != null) {
                    savedFile.close();
                }
            } catch (Exception e) {
                subDownloadListener.onFailed(index, e.getMessage());
            }
        }

        return FAIL;
    }

    @Override
    public void runOnUIThread(Integer integer) {
        if (integer == SUCCESS) {
            subDownloadListener.onSuccess(index);
        }
    }

    @Override
    public void onProgressUpdate(int progress) {
        subDownloadListener.onProgress(index, progress);
    }

    public void cancel() {
        isCanceled = true;
    }

    public void pause() {
        isPaused = true;
    }

    interface SubDownloadListener {
        void onProgress(int index, int progress);

        void onSuccess(int index);

        void onFailed(int index, String reason);
    }
}
