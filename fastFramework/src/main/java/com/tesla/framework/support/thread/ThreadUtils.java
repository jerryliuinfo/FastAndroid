package com.tesla.framework.support.thread;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by Jerry on 2019/2/7.
 */
public class ThreadUtils {


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
