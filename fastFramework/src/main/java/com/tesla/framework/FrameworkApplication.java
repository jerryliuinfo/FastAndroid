package com.tesla.framework;

import android.content.Context;

import com.tesla.framework.common.setting.SettingUtility;
import com.tesla.framework.common.util.ContextUtil;
import com.tesla.framework.common.util.DebugUtils;
import com.tesla.framework.common.util.network.NetworkHelper;

/**
 * Created by jerryliu on 2017/3/26.
 */

public class FrameworkApplication  {
    private static Context mContext;

    private FrameworkApplication() {
    }

    public static void onCreate(Context context){
        if (context != null){
            mContext = context.getApplicationContext();
        }
        ContextUtil.injectContext(mContext);
        NetworkHelper.getInstance().registerNetworkSensor(context);
        DebugUtils.syncDebugStatus(context);

        // 添加一些配置项
        SettingUtility.addSettings(context, "actions");


    }

    public static Context getContext(){
        return mContext;
    }




    public static Context getInstance(){
        return mContext;
    }
}
