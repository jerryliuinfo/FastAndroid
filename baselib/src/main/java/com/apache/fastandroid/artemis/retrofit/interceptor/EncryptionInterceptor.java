package com.apache.fastandroid.artemis.retrofit.interceptor;

import com.apache.fastandroid.artemis.retrofit.util.CodeMachine;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * Created by 01370340 on 2017/10/3.
 */

public class EncryptionInterceptor implements Interceptor{
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        RequestBody oldRequestBody = original.body();

        Buffer buffer = new Buffer();
        oldRequestBody.writeTo(buffer);

        String oldBodyStr = buffer.readUtf8();

        String newBodyStr = CodeMachine.encrypt(oldBodyStr);

        MediaType mediaType = MediaType.parse("text/plain; charset=utf-8");
        RequestBody newRequestBody = RequestBody.create(mediaType,newBodyStr);

        Request newRequest = original.newBuilder()
                .header("Content-Type", newRequestBody.contentType().toString())
                .header("Content-Length", String.valueOf(newRequestBody.contentLength()))
                .method(original.method(),newRequestBody)
                .build();
        return chain.proceed(newRequest);
    }
}
