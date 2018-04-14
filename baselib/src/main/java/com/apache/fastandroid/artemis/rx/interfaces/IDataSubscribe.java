package com.apache.fastandroid.artemis.rx.interfaces;

import com.apache.fastandroid.artemis.support.bean.BaseBean;

import io.reactivex.disposables.Disposable;

/**
 * Created by 01370340 on 2018/4/12.
 * 对数据格式为BaseBean的 请求结果处理接口
 */

public interface IDataSubscribe<T> {

    void doOnSubscribe(Disposable d);

    /**
     * 成功回调
     * @param baseData 基础泛型
     */
    void doOnNext(BaseBean<T> baseData);

    void doOnError(String msg);

    void doOnComplete();
}
