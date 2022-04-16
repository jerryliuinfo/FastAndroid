package com.apache.fastandroid.network.interceptor;


import com.tesla.framework.common.util.log.NLog;

import org.jetbrains.annotations.NotNull;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * Created by Jerry on 2021/3/23.
 */
public class HttpLogInterceptor implements Interceptor {
    public static final String TAG = "OKHttp";
    private static final Charset UTF8 = Charset.forName("UTF-8");
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        long t1 = System.nanoTime();
        RequestBody requestBody = request.body();
        boolean hasRequestBody = requestBody != null;
        if (hasRequestBody) {
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            Charset charset = UTF8;
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }
            NLog.d(TAG, "发送请求 %s on %s %n%s%s",request.url(), request.method(), request.headers(), this.isPlaintext(buffer) ? buffer.readString(charset) : "" );
        } else {
            NLog.d(TAG, "发送请求 %s on %s %n%s\"", request.url(), request.method(), request.headers());
        }

        Response response = chain.proceed(request);
        long t2 = System.nanoTime();
        ResponseBody responseBody = response.peekBody(1048576L);
        NLog.d(TAG, "接收响应(%.1fms): [%s] %n%s%s",  (double)(t2 - t1) / 1000000.0D, response.request().url(), response.headers(), responseBody.string());

        return response;
    }

    private boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long bufCount = Math.min(buffer.size(), 64L);
            buffer.copyTo(prefix, 0L, bufCount);

            for(int i = 0; i < 16 && !prefix.exhausted(); ++i) {
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }

            return true;
        } catch (EOFException var7) {
            return false;
        }
    }
}
