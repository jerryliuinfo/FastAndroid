package com.apache.fastandroid.artemis.http;

import android.text.TextUtils;

import com.apache.fastandroid.artemis.http.interceptor.AddCookiesInterceptor;
import com.apache.fastandroid.artemis.http.interceptor.CacheDataInterceptor;
import com.apache.fastandroid.artemis.retrofit.RetrofitClient;
import com.tesla.framework.applike.FrameworkApplication;
import com.tesla.framework.network.http.HttpClient;

import java.io.File;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * Created by 01370340 on 2018/4/12.
 * 网络请求工具类---使用的是全局配置的变量
 * 外部不要直接调用，通过HttpManager来调用
 */

public class GlobalHttp {

    private static volatile GlobalHttp instance = null;
    private GlobalHttp(){}
    public static GlobalHttp getInstance() {
        if (instance == null) {
            synchronized (GlobalHttp.class) {
                if (instance == null){
                    instance = new GlobalHttp();
                }
            }
        }
        return instance;
    }



    /**
     * 设置自己的client
     * @param client
     * @return
     */
    public GlobalHttp setOkHttpClient(OkHttpClient client){
        getGlobalRetrofitBuilder().client(client);
        return this;
    }

    /**
     * 是否开启请求日志
     *
     * @param isShowLog
     * @return
     */
    public GlobalHttp setLog(boolean isShowLog) {
        if (isShowLog) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    //Log.e("RxHttpUtils", message);
                }
            });
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            getGlobalOkHttpBuilder().addInterceptor(loggingInterceptor);
        }
        return this;
    }


    /**
     * 开启缓存，缓存到默认路径
     *
     * @return
     */
    public GlobalHttp setCache() {
        CacheDataInterceptor cacheInterceptor = new CacheDataInterceptor();
        Cache cache = new Cache(new File(FrameworkApplication.getContext().getExternalCacheDir()+ "/rxHttpCacheData")
                , 1024 * 1024 * 100);
        getGlobalOkHttpBuilder().addInterceptor(cacheInterceptor)
                .addNetworkInterceptor(cacheInterceptor)
                .cache(cache);
        return this;
    }

    /**
     * 设置缓存路径及缓存文件大小
     *
     * @param cachePath
     * @param maxSize
     * @return
     */
    public GlobalHttp setCache(String cachePath, long maxSize) {
        if (!TextUtils.isEmpty(cachePath) && maxSize > 0) {
            CacheDataInterceptor cacheInterceptor = new CacheDataInterceptor();
            Cache cache = new Cache(new File(cachePath), maxSize);
            getGlobalOkHttpBuilder()
                    .addInterceptor(cacheInterceptor)
                    .addNetworkInterceptor(cacheInterceptor)
                    .cache(cache);
        }

        return this;
    }

    /**
     *  全局持久话cookie,保存本地每次都会携带在header中
     * @param setCookie
     * @return
     */
    public GlobalHttp setCookie(boolean setCookie){
        if (setCookie){
            getGlobalOkHttpBuilder().addInterceptor(new AddCookiesInterceptor());
        }
        return this;
    }


    /**
     * 设置读取超时时间
     *
     * @param second
     * @return
     */
    public GlobalHttp setReadTimeout(long second) {
        getGlobalOkHttpBuilder().readTimeout(second, TimeUnit.SECONDS);
        return this;
    }

    /**
     * 设置写入超时时间
     *
     * @param second
     * @return
     */
    public GlobalHttp setWriteTimeout(long second) {
        getGlobalOkHttpBuilder().readTimeout(second, TimeUnit.SECONDS);
        return this;
    }

    /**
     * 设置连接超时时间
     *
     * @param second
     * @return
     */
    public GlobalHttp setConnectTimeout(long second) {
        getGlobalOkHttpBuilder().readTimeout(second, TimeUnit.SECONDS);
        return this;
    }


    /**
     * 信任所有证书,不安全有风险
     *
     * @return
     */
    public GlobalHttp setSslSocketFactory() {
        SSLUtils.SSLParams sslParams = SSLUtils.getSslSocketFactory();
        getGlobalOkHttpBuilder().sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
        return this;
    }

    /**
     * 使用预埋证书，校验服务端证书（自签名证书）
     *
     * @param certificates
     * @return
     */
    public GlobalHttp setSslSocketFactory(InputStream... certificates) {
        SSLUtils.SSLParams sslParams = SSLUtils.getSslSocketFactory(certificates);
        getGlobalOkHttpBuilder().sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
        return this;
    }

    /**
     * 使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
     *
     * @param bksFile
     * @param password
     * @param certificates
     * @return
     */
    public GlobalHttp setSslSocketFactory(InputStream bksFile, String password, InputStream... certificates) {
        SSLUtils.SSLParams sslParams = SSLUtils.getSslSocketFactory(bksFile, password, certificates);
        getGlobalOkHttpBuilder().sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
        return this;
    }



    public OkHttpClient.Builder getGlobalOkHttpBuilder(){
        return HttpClient.getInstance().getBuilder();
    }






    /****************************Retrofit相关*****************************************/

    /**
     * 设置baseUrl
     * @param baseUrl
     * @return
     */
    public static GlobalHttp setBaseUrl(String baseUrl){
        getGlobalRetrofitBuilder().baseUrl(baseUrl);
        return GlobalHttp.getInstance();
    }


    /**
     * 全局的 RetrofitBuilder
     * @return
     */
    public static Retrofit.Builder getGlobalRetrofitBuilder(){
        return RetrofitClient.getInstance().getRetrofitBuilder();
    }

    /**
     * 使用全局变量的请求, 非全局变量请求不要用这个方法
     *
     * @param clz
     * @param <K>
     * @return
     */
    public static <K> K createApi(Class<K> clz ){
        return RetrofitClient.getInstance().getRetrofit().create(clz);
    }


    /****************************Retrofit相关*****************************************/
}
