package com.tesla.framework.common.util.handler;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by 01370340 on 2018/4/15.
 * 线程池
 *
 */

public class ThreadUtils {
    /**
     * 默认核心线程是5个
     */
    private static final int CORE_POOL_SIZE = 5;
    /**
     * 默认执行最大线程是10个
     */
    private static final int MAXIMUM_POOL_SIZE = 10;

    private static final int KEEP_ALIVE = 60;

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "ThreadUtils #" + mCount.getAndIncrement());
        }
    };

    /**
     * 执行队列，默认是10个，超过10个后会开启新的线程，如果已运行线程大于 {@link #MAXIMUM_POOL_SIZE}，执行异常策略
     */
    private static final BlockingQueue<Runnable> sPoolWorkQueue = new LinkedBlockingQueue<>(10);

    /**
     * 默认线程池，最大执行{@link #CORE_POOL_SIZE}+{@link #MAXIMUM_POOL_SIZE}个线程
     */
    private static final Executor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS,
            sPoolWorkQueue, sThreadFactory);



    public static Executor getThreadPoolExecutor(){
        return THREAD_POOL_EXECUTOR;
    }



    private static Handler mHandler = new Handler(Looper.getMainLooper());
    /**
     * Be sure execute in main thread.
     *
     * @param runnable code
     */
    private void runInMainThread(Runnable runnable) {
        if (Looper.getMainLooper().getThread() != Thread.currentThread()) {
            mHandler.post(runnable);
        } else {
            runnable.run();
        }
    }

}
