package com.jef.variablefoundation.utils;

import android.util.Log;

import com.jef.variablefoundation.BuildConfig;

/**
 * Created by mr.lin on 2018/11/7
 * <p>
 * 日志打印类，自动屏蔽log
 */
public class Logger {

    private String TAG;

    public Logger() {
        this("Logger");
    }

    public Logger(String TAG) {
        this.TAG = TAG;
    }

    public Logger(Object object) {
        this(object.getClass());
    }

    public Logger(Class<?> c) {
        this(c.getSimpleName());
    }

    public void v(String msg) {
        if (BuildConfig.DEBUG) {
            Log.v(TAG, msg);
        }
    }

    public void d(String msg) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, msg);
        }
    }

    public void i(String msg) {
        if (BuildConfig.DEBUG) {
            Log.i(TAG, msg);
        }
    }

    public void w(String msg) {
        if (BuildConfig.DEBUG) {
            Log.w(TAG, msg);
        }
    }

    public void e(String msg) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, msg);
        }
    }

}
