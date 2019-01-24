package com.canceraide.mylibrary.net.download;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;

import com.canceraide.mylibrary.R;

/**
 * Created by mr.lin on 2018/12/8
 * 下载service
 */
public class DownloadService extends Service {

    private DownloadBinder mDownloadBinder;

    private DownloadTask mDownloadTask;
    private DownloadListener mDownloadListener;

    private String fileName;

    private int lastProgress;

    private static final int NOTIFY_DOWNLOADING_ID = 1;
    private static final int NOTIFY_DOWNLOAD_SUCCESS_ID = 2;
    private static final int NOTIFY_DOWNLOAD_FAIL_ID = 3;

    @Override
    public void onCreate() {
        super.onCreate();
        mDownloadBinder = new DownloadBinder();
        mDownloadListener = new DownloadListener() {
            @Override
            public void onProgress(int progress) {
                if (lastProgress == progress) {
                    return;
                } else {
                    getNotificationManager().notify(NOTIFY_DOWNLOADING_ID, getNotification("正在下载：", progress));
                    lastProgress = progress;
                }
            }

            @Override
            public void onSuccess() {
                getNotificationManager().cancel(NOTIFY_DOWNLOADING_ID);
                getNotificationManager().notify(NOTIFY_DOWNLOAD_SUCCESS_ID, getNotification("下载完成：", 100));
            }

            @Override
            public void onFailed(String reason) {
                getNotificationManager().cancel(NOTIFY_DOWNLOADING_ID);
                getNotificationManager().notify(NOTIFY_DOWNLOAD_FAIL_ID, getNotification("下载失败：", -1));
            }

            @Override
            public void onPaused() {

            }

            @Override
            public void onCanceled() {

            }
        };
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mDownloadBinder;
    }

    public class DownloadBinder extends Binder {

        public boolean startDownload(String remoteUrl, String directory, String fileName) {
            return startDownload(remoteUrl, directory, fileName, 1);
        }

        public boolean startDownload(String remoteUrl, String directory, String fileName, int threadCount) {
            DownloadService.this.fileName = fileName;
            if (mDownloadTask == null) {
                mDownloadTask = new DownloadTask(mDownloadListener, remoteUrl, directory, fileName, threadCount);
                mDownloadTask.execute();
                return true;
            } else {
                return false;
            }
        }

        public boolean pauseDownload() {
            if (mDownloadTask == null) {
                return false;
            } else {
                mDownloadTask.pause();
                return true;
            }
        }

        public boolean cancelDownload() {
            if (mDownloadTask == null) {
                return false;
            } else {
                mDownloadTask.cancel();
                return true;
            }
        }

    }

    private NotificationManager getNotificationManager() {
        return (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    private Notification getNotification(String title, int progress) {

        String id = "channel_01";
        String name = "下载通知";
        Notification.Builder builder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (progress > 0 && progress < 100) {
                NotificationChannel mChannel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_LOW);
                getNotificationManager().createNotificationChannel(mChannel);
                builder = new Notification.Builder(this)
                        .setChannelId(id)
                        .setContentTitle(title + fileName)
                        .setContentText(progress + "%")
                        .setProgress(100, progress, false)
                        .setSmallIcon(R.mipmap.ico_download);
            } else {
                NotificationChannel mChannel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH);
                getNotificationManager().createNotificationChannel(mChannel);
                builder = new Notification.Builder(this)
                        .setChannelId(id)
                        .setContentTitle(title + fileName)
                        .setSmallIcon(R.mipmap.ico_download);
            }
        } else {
            if (progress > 0 && progress < 100) {
                builder = new Notification.Builder(this)
                        .setContentTitle(title + fileName)
                        .setContentText(progress + "%")
                        .setProgress(100, progress, false)
                        .setSmallIcon(R.mipmap.ico_download)
                        .setOngoing(true);
            } else {
                builder = new Notification.Builder(this)
                        .setContentTitle(title + fileName)
                        .setDefaults(Notification.DEFAULT_SOUND)
                        .setProgress(100, progress, false)
                        .setSmallIcon(R.mipmap.ico_download)
                        .setOngoing(true);
            }
        }

        return builder.build();
    }

}
