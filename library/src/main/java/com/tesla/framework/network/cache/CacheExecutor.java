package com.tesla.framework.network.cache;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by jerryliu on 2017/4/8.
 */

public class CacheExecutor {
    static final int CORE_POOL_SIZE = 10;
    static final int MAXIMUM_POOL_SIZE = 128;
    static final int KEEP_ALIVE = 1;

    static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "BizlogicCacheTask #" + mCount.getAndIncrement());
        }
    };

    static final BlockingQueue<Runnable> sPoolWorkQueue = new LinkedBlockingQueue<>(10);

    public static final Executor CACHE_THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS,
            sPoolWorkQueue, sThreadFactory);

    public static Executor getCacheExecutor(){
        return CACHE_THREAD_POOL_EXECUTOR;
    }
}
