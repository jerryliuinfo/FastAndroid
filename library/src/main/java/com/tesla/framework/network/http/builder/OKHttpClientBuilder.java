package com.tesla.framework.network.http.builder;

import android.content.Context;

import com.tesla.framework.network.http.interceptor.CacheInterceptor;
import com.tesla.framework.network.http.interceptor.HeaderInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * Created by 01370340 on 2017/10/3.
 */

public class OKHttpClientBuilder {
    public static final int CACHE_SIZE = 4*1024*1024; //cache size
    public static final int NETWORK_TIMEOUT = 20; //network time out

    public static OkHttpClient.Builder getOKHttpClientBuilder(Context context,boolean isGzipEncode){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.cache(new Cache(context.getCacheDir(), CACHE_SIZE));
        builder.addInterceptor(new HeaderInterceptor());
        builder.addInterceptor(new CacheInterceptor(context,isGzipEncode));
        builder.addInterceptor(new HeaderInterceptor());
        builder.connectTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS);
        return builder;
    }
}
