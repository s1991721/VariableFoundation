package com.jef.variablefoundation.utils.async;


import android.os.Handler;
import android.os.Looper;
import android.support.annotation.UiThread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by mr.lin on 2018/11/16
 * 自定义AsyncTask
 */
public abstract class AsyncTaskVF<Result> implements Callable<Result> {

    //start
    //useless! only practice
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final int KEEP_ALIVE_SECONDS = 30;
    private static final ThreadFactory sThreadFactory = new ThreadFactory() {

        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "AsyncTaskVF from ljf # ID:" + mCount.getAndIncrement());
        }
    };

    private static final BlockingQueue<Runnable> sPoolWorkQueue = new LinkedBlockingQueue<>(32);

    private static final ExecutorService EXECUTOR;

    static {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_SECONDS, TimeUnit.SECONDS, sPoolWorkQueue, sThreadFactory);
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        EXECUTOR = threadPoolExecutor;
    }
    //end

    private static Handler handler = new Handler(Looper.getMainLooper());

    public void execute() {
        execute(EXECUTOR);
    }

    public void execute(ExecutorService executor) {
        FutureTask<Result> futureTask = new FutureTaskVF(this);
        executor.submit(futureTask);
    }

    @Override
    public Result call() throws Exception {
        return runOnBackGround();
    }

    public abstract Result runOnBackGround();

    @UiThread
    public abstract void runOnUIThread(Result result);

    @UiThread
    public void onProgressUpdate(int progress) {
    }

    protected void publishProgress(final int progress) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                onProgressUpdate(progress);
            }
        });
    }

    class FutureTaskVF extends FutureTask<Result> {
        public FutureTaskVF(Callable<Result> callable) {
            super(callable);
        }

        @Override
        protected void done() {
            Result result = null;
            try {
                result = get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            final Result finalResult = result;
            handler.post(new Runnable() {
                @Override
                public void run() {
                    runOnUIThread(finalResult);
                }
            });
        }
    }

}
