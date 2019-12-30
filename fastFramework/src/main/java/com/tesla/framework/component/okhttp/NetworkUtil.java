package com.tesla.framework.component.okhttp;

/**
 * Created by Jerry on 2019-12-29.
 */
public class NetworkUtil {

    private static <T,R> void sendRequest(String url,T requestData,Class<R> response,IJsonDataListener listener){
        IHttpRequest request = new JsonHttpRequest();
        CallbackListener callbackListener = new JsonCallbackListener<>(response,listener);
        HttpTask httpTask = new HttpTask(url,requestData,request,callbackListener);
        ThreadManager.getInstance().addTask(httpTask);
    }
}
