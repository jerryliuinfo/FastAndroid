package com.apache.fastandroid;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.support.multidex.MultiDexApplication;

import com.apache.fastandroid.support.exception.FastAndroidErrorMsg;
import com.apache.fastandroid.support.imageloader.GlideImageLoader;
import com.apache.fastandroid.support.report.ActivityLifeCycleReportCallback;
import com.tesla.framework.FrameworkApplication;
import com.tesla.framework.common.util.CrashHandler;
import com.tesla.framework.common.util.log.Logger;
import com.tesla.framework.common.util.log.NLog;
import com.tesla.framework.component.imageloader.ImageLoaderManager;
import com.tesla.framework.network.task.TaskException;
import com.tesla.framework.support.db.TeslaDB;
import com.tesla.framework.ui.activity.BaseActivity;
import com.tesla.framework.ui.widget.swipeback.SwipeActivityHelper;

/**
 * Created by jerryliu on 2017/3/26.
 */

public class MyApplication extends MultiDexApplication{
    public static final String TAG = MyApplication.class.getSimpleName();
    private static Context mContext;
    private ActivityLifecycleCallbacks activityLifecycleCallbacks;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        if (BuildConfig.LOG_DEBUG){
            NLog.setDebug(true, Logger.DEBUG);
        }
        FrameworkApplication.onCreate(getApplicationContext());
        TaskException.config(new FastAndroidErrorMsg());
        setUpCrashAndAnalysis();

        TeslaDB.setDB();
        BaseActivity.setHelper(SwipeActivityHelper.class);
        if (activityLifecycleCallbacks == null){
            activityLifecycleCallbacks = new ActivityLifeCycleReportCallback();
        }
        registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
        ImageLoaderManager.getInstance().setImageLoaderStrategy(new GlideImageLoader());
        BaseActivity.setHelper(FastActivityHelper.class);

    }



    private void setUpCrashAndAnalysis(){
        //本地crash日志收集
        CrashHandler.setupCrashHandler(getApplicationContext());
        //bugly统计
        //CrashReport.initCrashReport(getApplicationContext(),BuildConfig.BUGLY_APP_ID,BuildConfig.LOG_DEBUG);


    }

    public static Context getContext(){
        return mContext;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        if (activityLifecycleCallbacks != null){
            unregisterActivityLifecycleCallbacks(activityLifecycleCallbacks);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.fontScale != 1)//非默认值
            getResources();
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();
            //强制字体不随着系统改变而改变
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                createConfigurationContext(newConfig);
            } else {
                res.updateConfiguration(newConfig, res.getDisplayMetrics());
            }
        }
        return res;
    }
}
