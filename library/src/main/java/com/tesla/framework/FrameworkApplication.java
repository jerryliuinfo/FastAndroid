package com.tesla.framework;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.tesla.framework.common.util.DebugUtils;
import com.tesla.framework.common.util.network.NetworkHelper;

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
        NetworkHelper.getInstance().registerNetworkSensor(context);
        DebugUtils.syncDebugStatus(context);
        View view;
        ViewGroup viewGroup;
        LinearLayout linearLayout;
        //test Maven

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
