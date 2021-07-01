package com.tesla.framework.applike;

import android.app.Application;
import android.content.Context;

import com.tesla.framework.common.setting.SettingUtility;
import com.tesla.framework.common.util.network.NetworkHelper;
import com.tesla.framework.common.util.sp.SPUtil;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;

/**
 * Created by jerryliu on 2017/3/26.
 */

public class FrameworkApplication extends Application implements ViewModelStoreOwner {
    private static Context sContext;

    private static Application sApplication;

    private ViewModelStore mAppViewModelStore;


    @Override
    public void onCreate() {
        super.onCreate();
        mAppViewModelStore = new ViewModelStore();
        sContext = getApplicationContext();
        SPUtil.init(sContext,"");
        sApplication = (Application) sContext.getApplicationContext();
        NetworkHelper.getInstance().registerNetworkSensor(sContext);

        // 添加一些配置项
        SettingUtility.addSettings(sContext, "actions");
    }



    public static Context getContext(){
        return sContext;
    }

    public static Application getApplication(){
        return sApplication;
    }

    @NonNull
    @Override
    public ViewModelStore getViewModelStore() {
        return mAppViewModelStore;
    }
}
