package com.apache.fastandroid.network.retrofit;

import android.os.Build;

import com.apache.fastandroid.network.retrofit.convertor.CustomGsonConverterFactory;
import com.apache.fastandroid.retrofit.ApiConstant;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;



public final class ApiEngine {
    private static final Object car = new Object();
    private static final Object sword = new Object();

    public static final String TAG = "ApiEngine";

    private final static int CONN_TIMEOUT = 30000;
    private final static int READ_TIMEOUT = 30000;

    public static void main(String[] args) {

    }

    private static OkHttpClient sOkHttpClient;

    public static OkHttpClient getOkHttpClient(){
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
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .client(getOkHttpClient())
                    .baseUrl(ApiConstant.BASE_URL)
                    .build();
        }
        return sRetrofit;
    }

    private static void onOkHttpClientCreated(OkHttpClient.Builder client) {
        client.connectTimeout(CONN_TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true);
        //https://t.zsxq.com/nUnEune
        if (Build.VERSION.SDK_INT >= 28){
            client.pingInterval(3, TimeUnit.SECONDS);
        }

        List<Interceptor> interceptors = client.interceptors();
        interceptors.add(new HeaderInterceptor());
        interceptors.add(new ErrorInterceptor());
//        interceptors.add(new TokenInterceptor(SPUtils.getInstance("userInfo").getString("token")));
        HttpLogInterceptor logInterceptor = new HttpLogInterceptor();
        interceptors.add(logInterceptor);
    }


    public static <T> T getService(Class<T> cls) {
        final Retrofit retrofit = getRetrofit();
        return retrofit.create(cls);
    }

    private static ApiService sApiService;
    private static ApiServiceKt sApiServiceKt;
    public static ApiService getApiService() {
        if (sApiService == null){
            sApiService = getRetrofit().create(ApiService.class);
        }
        return getRetrofit().create(ApiService.class);
    }

    public static ApiServiceKt getApiServiceKt() {
        if (sApiServiceKt == null){
            sApiServiceKt = getRetrofit().create(ApiServiceKt.class);
        }
        return getRetrofit().create(ApiServiceKt.class);
    }

    private static FakeApi sFakeApi;
    public static FakeApi getFakeApi(){
        if (sFakeApi == null){
            sFakeApi = new FakeApi();
        }
        return sFakeApi;
    }




}
