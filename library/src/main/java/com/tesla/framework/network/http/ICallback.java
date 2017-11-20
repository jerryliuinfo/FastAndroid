package com.tesla.framework.network.http;

/**
 * Created by 01370340 on 2017/11/18.
 */

public interface ICallback {
    <T> void onResponse(T result);

    void onError(String code,String msg);
}
