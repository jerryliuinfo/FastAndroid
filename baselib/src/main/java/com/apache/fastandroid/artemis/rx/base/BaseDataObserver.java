package com.apache.fastandroid.artemis.rx.base;

import com.apache.fastandroid.artemis.exception.ApiException;
import com.apache.fastandroid.artemis.rx.interfaces.IDataSubscribe;
import com.apache.fastandroid.artemis.support.bean.BaseResponseBean;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by 01370340 on 2018/4/12.
 * 基类BaseObserver使用BaseData
 */

public abstract class BaseDataObserver<T> implements Observer<BaseResponseBean<T>>,IDataSubscribe<T> {

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        doOnSubscribe(d);
    }

    @Override
    public void onNext(@NonNull BaseResponseBean<T> baseBean) {
        doOnNext(baseBean);
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
