package com.apache.fastandroid.sample.messagequeue;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 用于get-set同步和唤醒
 * code come from ReactNative.
 */
public class SimpleSettableFuture<T> {
    private final CountDownLatch mReadyLatch = new CountDownLatch(1);
    private volatile T mResult;

    public SimpleSettableFuture() {
    }

    public void set(T result) {
        if (this.mReadyLatch.getCount() == 0L) {
            throw new RuntimeException("SealedResult has already been set!");
        } else {
            this.mResult = result;
            this.mReadyLatch.countDown();
        }
    }

    public T get(long timeoutMS) {
        try {
            if (!this.mReadyLatch.await(timeoutMS, TimeUnit.MILLISECONDS)) {
                throw new TimeoutException();
            }
        } catch (InterruptedException var4) {
            throw new RuntimeException(var4);
        }

        return this.mResult;
    }

    public static class TimeoutException extends RuntimeException {
        public TimeoutException() {
            super("Timed out waiting for future");
        }
    }
}
