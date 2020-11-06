package com.tesla.framework.common.util.task;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * author: jerry
 * created on: 2020/10/22 12:07 PM
 * description:
 */
public final class PriorityThreadFactory implements ThreadFactory {

    private final int mPriority;
    private final AtomicInteger mNumber = new AtomicInteger();
    private final String mName;

    public PriorityThreadFactory(String name, int priority) {
        mName = name;
        mPriority = priority;
    }

    public Thread newThread(Runnable r) {
        return new Thread(r, mName + '-' + mNumber.getAndIncrement()) {
            @Override
            public void run() {
                android.os.Process.setThreadPriority(mPriority);
                super.run();
            }
        };
    }

}