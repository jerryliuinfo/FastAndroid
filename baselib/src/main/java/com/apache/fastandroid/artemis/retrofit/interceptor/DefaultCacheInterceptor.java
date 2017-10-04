package com.apache.fastandroid.artemis.retrofit.interceptor;

import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * author: REXZOU<br>
 * 用于默认的缓存策略,需要权限android.permission.ACCESS_NETWORK_STATE,会判断的请求的Header中是否包含有MIGCache:enable，如果包括，则有缓存，否则不缓存
 * 示例，需要缓存 MIG-Cache:enable, online=30, offline=864000
 * 或使用默认时间 MIG-Cache:enable
 * 不需要缓存 不写Header或MIG-Cache:disable
 * date 2016/2/29 14:52 <br>
 * copyright TCL-MIG<br>
 */
public class DefaultCacheInterceptor implements Interceptor {

    private static final String TAG = "DefaultCacheInterceptor";
    public static final String DECREPT_KEY = "OGUiRDpWHv6B7cW&";
    private static final long DEFAULT_ONLINE_TIMEOUT = 60;  //60秒
    private static final long DEFAULT_OFFLINE_TIMEOUT = 60 * 60 * 24 * 28;  //一个月
    public static final String MIG_CACHE = "MIG-Cache";
    private final Context mContext;
    private final long mOnlineTimeout;
    private final long mOfflineTimout;
    //space+默认不进行压缩，鹰眼api时候需要设置为true
    private boolean isGzipEncode;

    public DefaultCacheInterceptor(Context context) {
        this(context, DEFAULT_ONLINE_TIMEOUT, DEFAULT_OFFLINE_TIMEOUT);
    }

    public DefaultCacheInterceptor(Context context, boolean gzipEncode){
        this(context);
        isGzipEncode=gzipEncode;
    }

    /**
     *
     * @param context
     * @param onlineTimeout 有网络时的缓存有效时间
     * @param offlineTimeout 无网络时的缓存有效时间
     */
    public DefaultCacheInterceptor(final Context context, final long onlineTimeout, final long offlineTimeout) {
        mContext = context.getApplicationContext();
        mOnlineTimeout = onlineTimeout;
        mOfflineTimout = offlineTimeout;
    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder builder = original.newBuilder();
        Request newRequest = builder.build();
        Response response = chain.proceed(newRequest);

        return response;
    }

}
