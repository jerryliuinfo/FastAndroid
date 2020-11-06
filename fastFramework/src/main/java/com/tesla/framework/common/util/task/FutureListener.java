package com.tesla.framework.common.util.task;


/**
 * author: jerry
 * created on: 2020/10/22 12:09 PM
 * description:
 */
public interface FutureListener<T> {
    void onFutureBegin(Future<T> future);

    void onFutureDone(Future<T> future);
}
