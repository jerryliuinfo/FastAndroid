package com.apache.fastandroid.network.interceptor;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Jerry on 2021/3/23.
 */
public class ErrorInterceptor implements Interceptor {
    public static final String TAG = ErrorInterceptor.class.getSimpleName();
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        String url = request.url().toString();

        Request originRequest = chain.request();
        Request.Builder builder = chain.request().newBuilder();

        if (url.contains("article/top/json")){
//            throw new IllegalArgumentException("invalid url" + originRequest.url());
        }
        return chain.proceed(builder.build());
    }
}
