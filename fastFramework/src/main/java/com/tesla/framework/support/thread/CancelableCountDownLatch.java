package com.tesla.framework.support.thread;

import java.util.concurrent.CountDownLatch;

/**
 * As its name.
 *
 */
public class CancelableCountDownLatch extends CountDownLatch {
    /**
     * Constructs a {@code CountDownLatch} initialized with the given count.
     *
     * @param count the number of times {@link #countDown} must be invoked
     *              before threads can pass through {@link #await}
     * @throws IllegalArgumentException if {@code count} is negative
     */
    public CancelableCountDownLatch(int count) {
        super(count);
    }

    /**
     * 将CountDownLatch计数清0
     */
    public void cancel() {
        while (getCount() > 0) {
            countDown();
        }
    }
}
