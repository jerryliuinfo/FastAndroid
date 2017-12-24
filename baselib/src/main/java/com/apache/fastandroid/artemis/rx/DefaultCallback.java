package com.apache.fastandroid.artemis.rx;

/**
 * Created by 01370340 on 2017/9/16.
 */

public abstract class DefaultCallback<T> implements ICallback<T> {


    @Override
    public void onFinished() {

    }

    @Override
    public void onFailed(Throwable e) {

    }

    public void onPrepare(){

    }
}
