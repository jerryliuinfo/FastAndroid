package com.tesla.framework.network.http;

/**
 * Created by 01370340 on 2017/11/18.
 */

public interface ICallback {
    void onResponse(String response);

    void onError(String code,String msg);
}
