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

    public OkHttpClient.Builder getBuilder(){
        return mBuilder;
    }


}
