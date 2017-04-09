package com.tesla.framework;

import android.content.Context;

import okhttp3.OkHttpClient;

/**
 * Created by jerryliu on 2017/3/26.
 */

public class FrameworkApplication  {
    private static Context mContext;
    private static  OkHttpClient mOkHttpClient = new OkHttpClient();

    public static void onCreate(Context context){
        if (context != null){
            mContext = context.getApplicationContext();
        }


    }

    public static Context getContext(){
        return mContext;
    }

    public static OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public static void configOkHttpClient(int connTimeout, int socketTimeout) {
        if(mOkHttpClient != null) {
            /*mOkHttpClient.((long)connTimeout, TimeUnit.MILLISECONDS);
            mOkHttpClient.setReadTimeout((long)socketTimeout, TimeUnit.MILLISECONDS);*/
        }

    }


    public static Context getInstance(){
        return mContext;
    }
}
