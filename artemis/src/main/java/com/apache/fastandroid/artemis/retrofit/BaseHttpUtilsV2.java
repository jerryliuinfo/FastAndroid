package com.apache.fastandroid.artemis.retrofit;

import android.content.Context;
import android.os.Bundle;

import com.apache.fastandroid.artemis.Constants;
import com.apache.fastandroid.artemis.comBridge.ModularizationDelegate;
import com.apache.fastandroid.artemis.api.TokenService;
import com.apache.fastandroid.artemis.support.bean.OAuth;
import com.apache.fastandroid.artemis.support.bean.Token;

import java.io.IOException;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Call;
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
public class BaseHttpUtilsV2 {

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

    public BaseHttpUtilsV2(Context context) {
        mContext = context.getApplicationContext();
    }

    public BaseHttpUtilsV2(Context context, String serverUrl) {
        mContext = context.getApplicationContext();
        mServerUrl = serverUrl;
    }
    public BaseHttpUtilsV2(Context context, boolean gzipEncode) {
        mContext = context.getApplicationContext();
        isGzipEncode = gzipEncode;
    }

    public Retrofit getRetrofit() {
        if (mRetrofit == null) {
            synchronized (BaseHttpUtilsV2.class) {
                if( mRetrofit == null) {
                    mRetrofit = initDefault();
                }
            }
        }

        // 如果当前没有缓存 token 或者请求已经附带 token 了，就不再添加
        try {
            Bundle result = ModularizationDelegate.getInstance().getData("com.apache.fastandroid:userCenter:getToken",null,null,new Object[]{});
            if (result != null){
                mTokenBean = (Token) result.getSerializable("token");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mRetrofit;
    }

    public static Hashtable<String,BaseHttpUtilsV2> cacheMap = new Hashtable<>();
    public static BaseHttpUtilsV2 getInstance(Context context, String serverUrl){
        BaseHttpUtilsV2 baseHttpUtils = cacheMap.get(serverUrl);
        if (baseHttpUtils == null){
            synchronized (BaseHttpUtilsV2.class){
                if (baseHttpUtils == null){
                    baseHttpUtils = new BaseHttpUtilsV2(context, serverUrl);
                    cacheMap.put(serverUrl, baseHttpUtils);
                }
            }
        }
        return baseHttpUtils;
    }
    public static BaseHttpUtilsV2 getInstance(Context context){

        return getInstance(context, DEFAULT_URL);
    }



    /**
     * 请在getRetrofit()之前调用
     * @param second 超时秒数 单位是秒
     */
    public void setNetworkTimeOut(int second) {
        mNetworkTimeOut = second;
    }


    Token mTokenBean = null;

    private Retrofit initDefault() {
        Retrofit.Builder builder = new Retrofit.Builder();
        if( mOKHttpClient == null) {
            OkHttpClient.Builder okBuilder = buildDefalutClient(mContext);
            if( mEnableLog) {
//                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//                interceptor.setLevel(mLogLevel);
//                okBuilder.addInterceptor(interceptor);
            }


            mOKHttpClient = okBuilder.build();
        }
        builder.client(mOKHttpClient);
        builder.addConverterFactory(new StringConverterFactory());
        builder.addConverterFactory(GsonConverterFactory.create());


        builder.baseUrl(mServerUrl);
        return builder.build();
    }



    // 为所有请求自动添加 token
    Interceptor mTokenInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();

            if (mTokenBean == null || alreadyHasAuthorizationHeader(originalRequest)) {
                return chain.proceed(originalRequest);
            }
            String token = OAuth.TOKEN_PREFIX + mTokenBean.getAccess_token();
            // 为请求附加 token
            Request authorised = originalRequest.newBuilder()
                    .header(OAuth.KEY_TOKEN, token)
                    .build();
            return chain.proceed(authorised);
        }
    };

    // 自动刷新 token
    Authenticator mAuthenticator = new Authenticator() {
        @Override
        public Request authenticate(Route route, Response response) {
            //Log.i("自动刷新 token 开始");

            TokenService tokenService = getRetrofit().create(TokenService.class);
            String accessToken = "";
            try {
                if (mTokenBean != null) {
                    Call<Token> call = tokenService.refreshToken(OAuth.client_id,
                            OAuth.client_secret, OAuth.GRANT_TYPE_REFRESH,
                            mTokenBean.getRefresh_token());
                    retrofit2.Response<Token> tokenResponse = call.execute();
                    Token token = tokenResponse.body();
                    if (token != null) {
                        mTokenBean = token;
                        //UserConfigManager.getInstance().saveToken(token);
                        ModularizationDelegate.getInstance().runStaticAction("com.apache.fastandroid:userCenter:saveToken",null,null,new Object[]{});
                        accessToken = token.getAccess_token();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //Log.i("自动刷新 token 结束：" + accessToken);
            return response.request().newBuilder()
                    .addHeader(OAuth.KEY_TOKEN, OAuth.TOKEN_PREFIX + accessToken)
                    .build();
        }
    };
    private boolean alreadyHasAuthorizationHeader(Request originalRequest) {
        // 用于 debug 时临时移除 token
        if (OAuth.getRemoveAutoTokenState()) {
            return true;
        }
        String token = originalRequest.header(OAuth.KEY_TOKEN);
        // 如果本身是请求 token 的 URL，直接返回 true
        // 如果不是，则判断 header 中是否已经添加过 Authorization 这个字段，以及是否为空
        return !(null == token || token.isEmpty() || originalRequest.url().toString().contains(Constants.OAUTH.OAUTH_URL));
    }




    public OkHttpClient.Builder buildDefalutClient(Context context) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.cache(new Cache(context.getCacheDir(), CACHE_SIZE));
        //builder.addInterceptor(new DefaultCacheInterceptor(context,isGzipEncode));
        builder.connectTimeout(mNetworkTimeOut, TimeUnit.SECONDS);
        builder.addNetworkInterceptor(mTokenInterceptor);   // 自动附加 token
        builder.authenticator(mAuthenticator);
        builder.readTimeout(mNetworkTimeOut, TimeUnit.SECONDS);
        builder.writeTimeout(mNetworkTimeOut, TimeUnit.SECONDS);
        return builder;
    }

}