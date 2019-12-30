package com.tesla.framework.component.okhttp;

import android.os.Handler;
import android.os.Looper;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Jerry on 2019-12-29.
 */
public class JsonCallbackListener<T> implements CallbackListener {

    private Class<T> responseClass;

    private IJsonDataListener mListener;

    private Handler mHandler = new Handler(Looper.getMainLooper());


    public JsonCallbackListener(Class<T> responseClass, IJsonDataListener mListener) {
        this.responseClass = responseClass;
        this.mListener = mListener;
    }

    @Override
    public void onSuccess(InputStream inputStream) {
        String response = getContent(inputStream);
        final T t = (T) JSON.parseObject(response);

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mListener.onSuccess(t);
            }
        });

    }

    @Override
    public void onFail(Throwable throwable) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mListener.onFailed();
            }
        });
    }

    private String getContent(InputStream inputStream){
        //对获取到的输入流进行读取
        String content;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream)); //将字节流转化成字符流
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null){
                sb.append(line +"\n");
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

        }
        return "";
    }
}
