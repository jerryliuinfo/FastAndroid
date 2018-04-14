package com.apache.fastandroid.artemis.rx.observer;

import com.apache.fastandroid.artemis.rx.RxUtil;
import com.apache.fastandroid.artemis.rx.base.BaseObserver;

import io.reactivex.disposables.Disposable;

/**
 * Created by 01370340 on 2018/4/12.
 */


public abstract class CommonObserver<T> extends BaseObserver<T>{
    @Override
    public void doOnSubscribe(Disposable d) {
        RxUtil.getInstance().addDisposable(d);
    }

    @Override
    public void doOnNext(T t) {
        onSuccess(t);
    }

    @Override
    public void doOnError(String msg) {
        onFailed(msg);
    }

    @Override
    public void doOnComplete() {
        onFinished();
    }


    /**
     * 成功回调
     *
     */
    protected abstract void onSuccess(T  t);

    protected abstract void onFailed(String msg);

    public void onFinished(){

    }




}
