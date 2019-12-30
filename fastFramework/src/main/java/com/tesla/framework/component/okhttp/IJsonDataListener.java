package com.tesla.framework.component.okhttp;

/**
 * Created by Jerry on 2019-12-29.
 */
public interface IJsonDataListener<T> {

    void onSuccess(T t);

    void onFailed();


}
