package com.tesla.framework;

import android.content.Context;

import com.tesla.framework.common.setting.SettingUtility;
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

        // 添加一些配置项
        SettingUtility.addSettings(context, "actions");
        SettingUtility.addSettings(context, "settings");


    }

    public static Context getContext(){
        return mContext;
    }

    public static OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public static void configOkHttpClient(int connTimeout, int socketTimeout) {
        if(mOkHttpClient != null) {
        }

    }


    public static Context getInstance(){
        return mContext;
    }
}
