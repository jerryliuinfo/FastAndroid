package com.apache.fastandroid.artemis.retrofit.builder;

import android.content.Context;

import com.apache.fastandroid.artemis.retrofit.interceptor.DefaultCacheInterceptor;
import com.apache.fastandroid.artemis.retrofit.interceptor.HeaderInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * Created by 01370340 on 2017/10/3.
 */

public class OKHttpClientBuilder {
    public static final int CACHE_SIZE = 4*1024*1024; //cache size
    public static final int mNetworkTimeOut = 20; //network time out

    public static OkHttpClient.Builder getOKHttpClientBuilder(Context context,boolean isGzipEncode){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.cache(new Cache(context.getCacheDir(), CACHE_SIZE));
        builder.addInterceptor(new HeaderInterceptor());
        builder.addInterceptor(new DefaultCacheInterceptor(context,isGzipEncode));
        builder.addInterceptor(new HeaderInterceptor());
        builder.connectTimeout(mNetworkTimeOut, TimeUnit.SECONDS);
        builder.readTimeout(mNetworkTimeOut, TimeUnit.SECONDS);
        builder.writeTimeout(mNetworkTimeOut, TimeUnit.SECONDS);
        return builder;
    }
}
