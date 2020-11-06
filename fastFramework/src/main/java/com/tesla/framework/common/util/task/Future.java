package com.tesla.framework.common.util.task;

/**
 * author: jerry
 * created on: 2020/10/22 12:10 PM
 * description:
 */
public interface Future<T> {
    public void cancel();

    public boolean isCancelled();

    public boolean isDone();

    public T get();

    public void waitDone();
}

