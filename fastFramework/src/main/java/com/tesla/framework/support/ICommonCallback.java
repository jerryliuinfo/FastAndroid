package com.tesla.framework.support;

/**
 * Created by Jerry on 2021/1/8.
 */
public interface ICommonCallback<T> {
    void onSucceed(T t);
    void onFailed(KidsException exception);

    default void onFinish(){}

}

