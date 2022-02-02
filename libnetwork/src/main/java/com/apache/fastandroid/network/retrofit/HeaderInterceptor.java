package com.apache.fastandroid.network.retrofit;

import android.os.Build;

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
            //添加公共请求头
            builder.addHeader("brand", Build.BRAND)
                    .addHeader("model", Build.MODEL);
        } catch (Exception e) {
            NLog.printStackTrace(TAG, e);
        }
        return chain.proceed(builder.build());
    }
}
