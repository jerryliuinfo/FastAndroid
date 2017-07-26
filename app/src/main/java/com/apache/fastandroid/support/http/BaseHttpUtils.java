package com.apache.fastandroid.support.http;

import android.content.Context;

import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 * @desc A tool based on http, using Okhttp and Retrofit to get/post request.
 *
 * @date 2016/2/18 19:24
 * @copyright TCL-MIG
 * @author REXZOU
 */
public class BaseHttpUtils {

    public static final String TAG = "BaseHttpUtils";

    public static final int CACHE_SIZE = 4*1024*1024; //cache size
    public static final int NETWORK_TIME_OUT = 20; //network time out

    private static OkHttpClient sDefaultHttpClient;

    private Context mContext;

    private Retrofit mRetrofit;

    private OkHttpClient mOKHttpClient;

    private String mServerUrl; //server url

    private static String DEFAULT_URL = "http://cleanportal-test.tclclouds.com/";

    private boolean mEnableLog;

    //private HttpLoggingInterceptor.Level mLogLevel;

    private int mNetworkTimeOut = NETWORK_TIME_OUT;

    //space+默认不进行压缩，鹰眼api时候需要设置为true
    private boolean isGzipEncode;

    public BaseHttpUtils(Context context) {
        mContext = context.getApplicationContext();
    }

    public BaseHttpUtils(Context context, String serverUrl) {
        mContext = context.getApplicationContext();
        mServerUrl = serverUrl;
    }
    public BaseHttpUtils(Context context, boolean gzipEncode) {
        mContext = context.getApplicationContext();
        isGzipEncode = gzipEncode;
    }


   /* public BaseHttpUtils(Context context, String serverUrl, OkHttpClient okHttpClient) {
        mContext = context.getApplicationContext();
        mServerUrl = serverUrl;
        mOKHttpClient = okHttpClient;
    }
*/
    public Retrofit getRetrofit() {
        if (mRetrofit == null) {
            synchronized (BaseHttpUtils.class) {
                if( mRetrofit == null) {
                    mRetrofit = initDefault();
                }
            }
        }
        return mRetrofit;
    }

    public static Hashtable<String,BaseHttpUtils> cacheMap = new Hashtable<>();
//    private static volatile BaseHttpUtils baseHttpUtils;
    public static BaseHttpUtils getInstance(Context context, String serverUrl){
        BaseHttpUtils baseHttpUtils = cacheMap.get(serverUrl);
        if (baseHttpUtils == null){
            synchronized (BaseHttpUtils.class){
                if (baseHttpUtils == null){
                    baseHttpUtils = new BaseHttpUtils(context, serverUrl);
                    cacheMap.put(serverUrl, baseHttpUtils);
                }
            }
        }
        return baseHttpUtils;
    }
    public static BaseHttpUtils getInstance(Context context){

        return getInstance(context, DEFAULT_URL);
    }






    /**
     * 设置日志级别
     * @param enable
     * @param logLevel
     */
//    public void setLogLevel(boolean enable, HttpLoggingInterceptor.Level logLevel) {
//        mEnableLog = enable;
//        mLogLevel = logLevel;
//    }

    /**
     * 请在getRetrofit()之前调用
     * @param second 超时秒数 单位是秒
     */
    public void setNetworkTimeOut(int second) {
        mNetworkTimeOut = second;
    }

    private Retrofit initDefault() {
        Retrofit.Builder builder = new Retrofit.Builder();
        if( mOKHttpClient == null) {
            OkHttpClient.Builder okBuilder = buildDefalutClient(mContext);
            if( mEnableLog) {
//                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//                interceptor.setLevel(mLogLevel);
//                okBuilder.addInterceptor(interceptor);
                mOKHttpClient = okBuilder.build();
            }
            else {
                mOKHttpClient = getDefaultOkHttpClient(okBuilder);
            }


        }
        builder.client(mOKHttpClient);
        builder.addConverterFactory(new StringConverterFactory());
        builder.addConverterFactory(GsonConverterFactory.create());
        builder.baseUrl(mServerUrl);
        return builder.build();
    }

    /**
     * Generate a default OKHttpClient with default parameter.
     * If you have special requirements, please create by yourself.
     * @return
     */
    public static synchronized OkHttpClient getDefaultOkHttpClient(OkHttpClient.Builder builder ) {
        if( sDefaultHttpClient == null) {
//        builder.addInterceptor()
            sDefaultHttpClient = builder.build();
        }
        return sDefaultHttpClient;
    }

    public OkHttpClient.Builder buildDefalutClient(Context context) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.cache(new Cache(context.getCacheDir(), CACHE_SIZE));
        //builder.addInterceptor(new DefaultCacheInterceptor(context,isGzipEncode));
        builder.connectTimeout(mNetworkTimeOut, TimeUnit.SECONDS);
        builder.readTimeout(mNetworkTimeOut, TimeUnit.SECONDS);
        builder.writeTimeout(mNetworkTimeOut, TimeUnit.SECONDS);
        return builder;
    }


//    public static void setDefaultUrl(String defaultUrl) {
//        DEFAULT_URL = defaultUrl;
//    }
}
