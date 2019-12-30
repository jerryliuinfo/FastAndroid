package com.tesla.framework.component.okhttp;

import android.support.annotation.NonNull;

import com.alibaba.fastjson.JSON;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jerry on 2019-12-29.
 */
public class HttpTask<T> implements Runnable, Delayed {

    private IHttpRequest mHttpRequest;

    public HttpTask(String url, T requestData,IHttpRequest request, CallbackListener listener){
        request.setUrl(url);
        request.setListener(listener);

        String content = JSON.toJSONString(requestData);
        try {
            request.setData(content.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        mHttpRequest = request;
    }


    @Override
    public void run() {
        try {
            mHttpRequest.execute();
        }catch (Exception e){
            ThreadManager.getInstance().addDelayTask(this);
        }
    }

    private long delayTime;

    private int retryCount;

    public long getDelayTime() {
        return delayTime;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setDelayTime(long delayTime) {
        this.delayTime = System.currentTimeMillis() + delayTime;
    }



    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    @Override
    public long getDelay(@NonNull TimeUnit unit) {
        return unit.convert(this.delayTime - System.currentTimeMillis(),TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(@NonNull Delayed o) {
        return 0;
    }
}
