package com.apache.fastandroid.retrofit;

import android.content.Context;

import com.apache.fastandroid.app.FastApplication;
import com.apache.fastandroid.retrofit.convertor.CustomGsonConverterFactory;
import com.apache.fastandroid.sample.singleton.Singleton;
import com.tesla.framework.common.util.log.NLog;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;


/**
 * description: 连接
 * author hui.zhu
 * data: 2020-04-24
 * copyright TCL-MIBC
 */
public final class ApiEngine {
    public static final String TAG = "ApiEngine";

    private final static int CONN_TIMEOUT = 30000;
    private final static int READ_TIMEOUT = 30000;
    private static String userAgent;

    public static String API_HOST = "";

    private static Singleton<OkHttpClient, Void> SINGLE_CLIENT = new Singleton<OkHttpClient, Void>() {
        @Override
        protected OkHttpClient create(Void v) {
            OkHttpClient.Builder mBuilder = new OkHttpClient().newBuilder();
            onOkHttpClientCreated(mBuilder);
            return mBuilder.build();
        }
    };

    private static final Singleton<Retrofit, Void> sRetrofitMain = new Singleton<Retrofit, Void>() {
        @Override
        protected Retrofit create(Void v) {
            OkHttpClient client = getOkHttpClient();
            Retrofit.Builder builder = new Retrofit.Builder();
            return builder
                    .addConverterFactory(CustomGsonConverterFactory.create())
                    .client(client)
                    .baseUrl(API_HOST)
                    .build();
        }
    };

    private static void onOkHttpClientCreated(OkHttpClient.Builder client) {
        client.connectTimeout(CONN_TIMEOUT, TimeUnit.MILLISECONDS);
        client.readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS);
        final Context context = FastApplication.getContext();
        List<Interceptor> interceptors = client.interceptors();
        interceptors.add(new HeaderInterceptor());

        if (NLog.isDebug()) {
            HttpLogInterceptor logInterceptor = new HttpLogInterceptor();
            interceptors.add(logInterceptor);
        }
    }

    /**
     * 外部调用需要通过来创建和获取单例
     *
     * @return
     */
    public static OkHttpClient getOkHttpClient() {
        return SINGLE_CLIENT.get();
    }

    public static <T> T createMainService(Class<T> cls) {
        final Retrofit retrofit = sRetrofitMain.get();
        return retrofit.create(cls);
    }



}
