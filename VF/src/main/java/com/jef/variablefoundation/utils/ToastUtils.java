package com.jef.variablefoundation.utils;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

import com.jef.variablefoundation.base.BaseApplication;

/**
 * Created by mr.lin on 2018/12/4
 * toast工具
 */
public class ToastUtils {

    private static Toast mToast;
    private static String lastMsg;

    public static void showToast(int resId) {
        showToast(BaseApplication.getInstance().getString(resId));
    }

    @SuppressLint("ShowToast")
    public static void showToast(final String msg) {

        if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    showToastMainThread(msg);
                }
            });
        } else {
            showToastMainThread(msg);
        }
    }

    private static void showToastMainThread(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        if (mToast == null) {
            mToast = Toast.makeText(BaseApplication.getInstance(), msg, Toast.LENGTH_SHORT);
        } else {
            if (!lastMsg.equals(msg)) {
                mToast.setText(msg);
            }
        }
        lastMsg = msg;
        mToast.show();
    }

}
