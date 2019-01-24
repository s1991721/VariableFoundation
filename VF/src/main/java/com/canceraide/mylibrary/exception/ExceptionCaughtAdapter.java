package com.canceraide.mylibrary.exception;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * Created by mr.lin on 2018/11/13
 * 逃逸异常接口实现类
 */
public class ExceptionCaughtAdapter implements UncaughtExceptionHandler {

    private UncaughtExceptionHandler uncaughtExceptionHandler;

    public ExceptionCaughtAdapter(UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.uncaughtExceptionHandler = uncaughtExceptionHandler;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        ExceptionActivity.showException(e);
        if (uncaughtExceptionHandler != null) {
            uncaughtExceptionHandler.uncaughtException(t, e);
        }
    }

}
