package com.apache.fastandroid.artemis.retrofit;

import android.content.Context;

import com.apache.fastandroid.artemis.retrofit.builder.OKHttpClientBuilder;

import java.util.Hashtable;

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
     * 请在getRetrofit()之前调用
     * @param second 超时秒数 单位是秒
     */
    public void setNetworkTimeOut(int second) {
        mNetworkTimeOut = second;
    }

    private Retrofit initDefault() {
        Retrofit.Builder builder = new Retrofit.Builder();
        if( mOKHttpClient == null) {
            OkHttpClient.Builder okBuilder = OKHttpClientBuilder.getOKHttpClientBuilder(mContext,isGzipEncode);
            mOKHttpClient = okBuilder.build();

        }
        builder.client(mOKHttpClient);
        builder.addConverterFactory(new StringConverterFactory());
        builder.addConverterFactory(GsonConverterFactory.create());
        builder.baseUrl(mServerUrl);
        return builder.build();
    }





}
