package com.apache.fastandroid.artemis.rx;

/**
 * Created by 01370340 on 2017/9/4.
 */

public  class DefaultHttpResultObserver<T> extends HttpResultObserver<T> {
    ICallback<T> callback;

    public DefaultHttpResultObserver(ICallback<T> callback) {
        this.callback = callback;
    }

    @Override
    public void onSuccess(T t) {
        if (callback != null){
            callback.onSuccess(t);

        }
    }

    @Override
    public void onFailed(Throwable e) {
        if (callback != null){
            callback.onFailed(e);
        }

    }

    @Override
    public void onFinished() {
        if (callback != null){
            callback.onFinished();
        }

    }
}
