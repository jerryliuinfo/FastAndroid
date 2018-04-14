package com.apache.fastandroid.artemis.rx.interfaces;

import io.reactivex.disposables.Disposable;

/**
 * Created by 01370340 on 2018/4/12.
 * 定义请求结果处理接口(普通数据)
 */

public interface ISubscribe<T> {

    void doOnSubscribe(Disposable d);

    void doOnNext(T t);

    void doOnError(String msg);

    void doOnComplete();

}
