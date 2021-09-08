package com.apache.fastandroid.network.retrofit;

import com.apache.fastandroid.network.retrofit.convertor.CustomGsonConverterFactory;
import com.apache.fastandroid.retrofit.ApiConstant;
import com.mooc.libnetwork.BuildConfig;

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
                    .baseUrl(ApiConstant.BASE_URL)
                    .build();
        }
    };

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

    /**
     * 外部调用需要通过来创建和获取单例
     *
     * @return
     */
    public static OkHttpClient getOkHttpClient() {
        return SINGLE_CLIENT.get();
    }

    public static <T> T createApiService(Class<T> cls) {
        final Retrofit retrofit = sRetrofitMain.get();
        return retrofit.create(cls);
    }
    public static ApiService createApiService() {
        final Retrofit retrofit = sRetrofitMain.get();
        return retrofit.create(ApiService.class);
    }


    private static abstract class Singleton<T, P> {
        /**
         * 唯一实例
         */
        private volatile T mInstance;

        /**
         * 创建实例
         *
         * @param p 创建实例所需指定的参数
         *          exp: Context context
         * @return 实例
         */
        protected abstract T create(P p);

        /**
         * 获取实例
         *
         * @param p 创建实例时指定的参数
         *          exp: Context context
         * @return 实例
         */
        public final T get(P p) {
            if (mInstance == null) {
                synchronized (this) {
                    if (mInstance == null) {
                        mInstance = create(p);
                    }
                }
            }
            return mInstance;
        }

        public final T get() {
            return get(null);
        }
    }


}
