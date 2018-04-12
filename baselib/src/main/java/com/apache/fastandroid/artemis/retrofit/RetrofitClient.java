package com.apache.fastandroid.artemis.retrofit;

import com.tesla.framework.network.http.HttpClient;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 01370340 on 2018/4/11.
 */

public class RetrofitClient {
    private static volatile RetrofitClient instance = null;
    private Retrofit.Builder mRetrofitBuilder;
    private OkHttpClient.Builder mOkHttpBuilder;

    private RetrofitClient(){
        mOkHttpBuilder = HttpClient.getInstance().getBuilder();
        mRetrofitBuilder = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create());
    }
    public static RetrofitClient getInstance() {
        if (instance == null) {
            synchronized (RetrofitClient.class) {
                if (instance == null){
                    instance = new RetrofitClient();
                }
            }
        }
        return instance;
    }

    public Retrofit.Builder getRetrofitBuilder(){
        return mRetrofitBuilder;
    }

    public Retrofit getRetrofit(){
        return mRetrofitBuilder.client(mOkHttpBuilder.build()).build();
    }



}
