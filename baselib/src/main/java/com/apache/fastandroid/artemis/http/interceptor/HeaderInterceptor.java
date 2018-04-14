package com.apache.fastandroid.artemis.http.interceptor;

import java.io.IOException;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 01370340 on 2018/4/12.
 * 消息头拦截器
 */

public class HeaderInterceptor implements Interceptor{
    private Map<String,Object> headerMap;

    public HeaderInterceptor(Map<String, Object> headerMap) {
        this.headerMap = headerMap;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder();
        String url = request.url().toString();
        if (headerMap != null && !headerMap.isEmpty()){
            for (Map.Entry<String, Object> entry : headerMap.entrySet()) {
                requestBuilder.addHeader(entry.getKey(), (String) entry.getValue());
            }
        }
        /*if (url.contains("book-list/")){
            requestBuilder.addHeader("Host", "api.zhuishushenqi.com");
        }*/
        return chain.proceed(requestBuilder.build());
    }
}
