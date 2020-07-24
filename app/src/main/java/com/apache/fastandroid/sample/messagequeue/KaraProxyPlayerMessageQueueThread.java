package com.apache.fastandroid.sample.messagequeue;

import android.os.Looper;

import com.apache.fastandroid.util.MainLogUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jerry on 2020/6/7.
 */
class KaraProxyPlayerMessageQueueThread {
    public static final String TAG = "KaraProxyPlayerMessageQueueThread";
    private final String mName;
    private final Looper mLooper;
    private final MessageQueueThreadHandler mHandler;
    private volatile boolean mIsFinished = false;
    //private static ConcurrentHashMap<KaraProxyPlayer, KaraProxyPlayerMessageQueueThread> instance = null;

    /**
     * 线程创建超时时间，超过这个时间直接丢exception
     */
    public static final long SET_LOOPER_TIME = 5000L;

    /**
     * TODO exceptionHandler
     * 构造函数
     *
     * @param name
     * @param looper
     * @param exceptionHandler
     */
    private KaraProxyPlayerMessageQueueThread(String name, Looper looper, QueueThreadExceptionHandler exceptionHandler) {
        this.mName = name;
        this.mLooper = looper;
        this.mHandler = new MessageQueueThreadHandler(looper, exceptionHandler);
    }

    /**
     * 给线程队列enqueue任务
     * finish标志位变化后，仍然可以继续接受任务
     *
     * @param runnable
     */
    public void runOnQueue(Runnable runnable) {
        if (this.mIsFinished) {
            MainLogUtil.d("Tried to enqueue runnable on already finished thread: " + this.getName());
        }
        // 当前线程不是该线程，则将runnable抛到队列；反之，则直接执行
        if (!isOnThread()) {
            this.mHandler.post(runnable);
        } else {
            runnable.run();
        }
    }

    public Looper getLooper() {
        return this.mLooper;
    }

    public String getName() {
        return this.mName;
    }


    /**
     * 当前的运行线程是否自己
     *
     * @return
     */
    public boolean isOnThread() {
        return this.mLooper.getThread() == Thread.currentThread();
    }

    /**
     * 当前时间，用于给线程默认名字
     *
     * @return
     */
    private static String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH_mm_ss");
        Date date = new Date();
        return sdf.format(date);
    }

    /**
     * 当前线程是否已经被调用过quit
     *
     * @return
     */
    public boolean isFinished() {
        return mIsFinished;
    }
}
