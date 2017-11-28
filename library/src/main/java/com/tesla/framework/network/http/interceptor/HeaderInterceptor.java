package com.tesla.framework.network.http.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 01370340 on 2017/10/3.
 */

public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        String url = original.url().toString();
        if (url.contains("book/") ||
                url.contains("book-list/") ||
                url.contains("toc/") ||
                url.contains("post/") ||
                url.contains("user/")) {
            Request request = original.newBuilder()
                    .addHeader("User-Agent", "ZhuiShuShenQi/3.40[preload=false;locale=zh_CN;clientidbase=android-nvidia]") // 不能转UTF-8
                    .addHeader("Host", "api.zhuishushenqi.com")
                    .addHeader("Connection", "Keep-Alive")
                    .addHeader("If-None-Match", "W/\"2a04-4nguJ+XAaA1yAeFHyxVImg\"")
                    .addHeader("If-Modified-Since", "Tue, 02 Aug 2016 03:20:06 UTC")
                    .build();
            return chain.proceed(request);
        }else if (url.contains("c.m.163.com")){
            Request build = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .build();
            return chain.proceed(build);
        }



        return chain.proceed(original);
    }
}
