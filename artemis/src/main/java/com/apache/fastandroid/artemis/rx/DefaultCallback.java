package com.apache.fastandroid.artemis.rx;

/**
 * Created by 01370340 on 2017/9/16.
 */

public interface DefaultCallback<T> {
    void onSuccess(T t);

    void onFailed(Throwable e);

    void onFinished();
}
