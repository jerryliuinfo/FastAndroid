package com.tesla.framework.applike;

import android.app.Application;
import android.content.Context;

import com.tesla.framework.common.setting.SettingUtility;
import com.tesla.framework.common.util.network.NetworkHelper;
import com.tesla.framework.common.util.sp.SPUtil;

/**
 * Created by jerryliu on 2017/3/26.
 */

public class FrameworkApplication  {
    private static Context sContext;

    private static Application sApplication;
    private FrameworkApplication() {
    }

    public static void onCreate(Context context){
        if (context == null){
            throw new NullPointerException("context can't be null");
        }
        sContext = context.getApplicationContext();
        SPUtil.init(context,"");
        sApplication = (Application) context.getApplicationContext();
        NetworkHelper.getInstance().registerNetworkSensor(context);

        // 添加一些配置项
        SettingUtility.addSettings(context, "actions");


    }


    public static void onDestroy(){

    }

    public static Context getContext(){
        return sContext;
    }

    public static Application getApplication(){
        return sApplication;
    }

}
