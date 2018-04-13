package com.tesla.framework.network.http;

import okhttp3.OkHttpClient;

/**
 * Created by 01370340 on 2018/4/11.
 */

public class HttpClient {

    private static volatile HttpClient instance = null;
    private  OkHttpClient.Builder mBuilder;
    private HttpClient(){
        mBuilder = new OkHttpClient.Builder();
    }
   public static HttpClient getInstance() {
       if (instance == null) {
           synchronized (HttpClient.class) {
               if (instance == null){
                   instance = new HttpClient();
               }
           }
       }
       return instance;
   }

    /**
     * 上层模块可以拿到全局的OkHttpClient.Builder设置参数
     * @return
     */
    public OkHttpClient.Builder getBuilder(){
        return mBuilder;
    }





}
