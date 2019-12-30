package com.tesla.framework.component.okhttp;

/**
 * Created by Jerry on 2019-12-29.
 */
public interface IHttpRequest {

    void setUrl(String url);

    void setData(byte[] data);

    void execute();

    void setListener(CallbackListener listener);
}
