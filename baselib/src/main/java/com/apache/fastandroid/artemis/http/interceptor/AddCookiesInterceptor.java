package com.apache.fastandroid.artemis.http.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * <p>
 *
 * 请求头里边添加cookie
 */

public class AddCookiesInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
       /* HashSet<String> preferences = (HashSet<String>) SPUtil.getSet(SPKeys.COOKIE, new HashSet<String>());
        if (preferences != null) {
            for (String cookie : preferences) {
                builder.addHeader("Cookie", cookie);
                // This is done so I know which headers are being added; this interceptor is used after the normal logging of OkHttp
                Log.v("RxHttpUtils", "Adding Header Cookie--->: " + cookie);
            }
        }*/
        return chain.proceed(builder.build());
    }

}
