package com.apache.fastandroid.artemis.rx.base;

import com.apache.fastandroid.artemis.exception.ApiException;
import com.apache.fastandroid.artemis.rx.interfaces.ISubscribe;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by 01370340 on 2018/4/12.
 * 自定义回调方法封装，对应rxjava生命周期回调
 */

public abstract class BaseObserver<T> implements Observer<T>,ISubscribe<T> {

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        doOnSubscribe(d);
    }

    @Override
    public void onNext(@NonNull T t) {
        doOnNext(t);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        doOnError(getErrorMsg(e));
    }


    public String getErrorMsg(Throwable e){
        return ApiException.handleException(e).getMessage();
    }

    @Override
    public void onComplete() {
        doOnComplete();
    }



}
