package com.apache.fastandroid.network.retrofit;

import com.apache.fastandroid.network.retrofit.convertor.CustomGsonConverterFactory;
import com.apache.fastandroid.retrofit.ApiConstant;
import com.mooc.libnetwork.BuildConfig;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


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

    private static OkHttpClient sOkHttpClient;

    private static OkHttpClient getOkHttpClient(){
        if (sOkHttpClient == null){
            OkHttpClient.Builder mBuilder = new OkHttpClient().newBuilder();
            onOkHttpClientCreated(mBuilder);
            sOkHttpClient = mBuilder.build();
        }
        return sOkHttpClient;
    }

    private static Retrofit sRetrofit;
    private static Retrofit getRetrofit(){
        if (sRetrofit == null){
            Retrofit.Builder builder = new Retrofit.Builder();
            sRetrofit =  builder
                    .addConverterFactory(CustomGsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(getOkHttpClient())
                    .baseUrl(ApiConstant.BASE_URL)
                    .build();
        }
        return sRetrofit;
    }

    private static void onOkHttpClientCreated(OkHttpClient.Builder client) {
        client.connectTimeout(CONN_TIMEOUT, TimeUnit.MILLISECONDS);
        client.readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS);
        List<Interceptor> interceptors = client.interceptors();
        interceptors.add(new HeaderInterceptor());
        if (BuildConfig.DEBUG) {
            HttpLogInterceptor logInterceptor = new HttpLogInterceptor();
            interceptors.add(logInterceptor);
        }
    }


    public static <T> T getService(Class<T> cls) {
        final Retrofit retrofit = getRetrofit();
        return retrofit.create(cls);
    }

    private static ApiService sApiService;
    public static ApiService createApiService() {
        if (sApiService == null){
            sApiService = getRetrofit().create(ApiService.class);
        }
        return getRetrofit().create(ApiService.class);
    }


    private static FakeApi sFakeApi;
    public static FakeApi getFakeApi(){
        if (sFakeApi == null){
            sFakeApi = new FakeApi();
        }
        return sFakeApi;
    }




}
