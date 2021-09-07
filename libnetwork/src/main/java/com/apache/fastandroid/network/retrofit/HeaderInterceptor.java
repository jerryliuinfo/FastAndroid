package com.apache.fastandroid.retrofit;

import com.tesla.framework.common.util.log.NLog;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Jerry on 2021/3/23.
 */
public class HeaderInterceptor implements Interceptor {
    public static final String TAG = HeaderInterceptor.class.getSimpleName();
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        String url = request.url().toString();


        Request.Builder builder = chain.request().newBuilder();
        try {
            builder.addHeader("c", "US")
                    .addHeader("l", "en");
        } catch (Exception e) {
            NLog.printStackTrace(TAG, e);
        }
        return chain.proceed(builder.build());
    }
}
