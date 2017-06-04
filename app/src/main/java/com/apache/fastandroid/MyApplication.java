package com.apache.fastandroid;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.apache.fastandroid.support.report.ActivityLifeCycleReportCallback;
import com.tencent.bugly.crashreport.CrashReport;
import com.tesla.framework.FrameworkApplication;
import com.tesla.framework.common.util.CrashHandler;
import com.tesla.framework.common.util.log.Logger;
import com.tesla.framework.common.util.log.NLog;
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
        setUpCrashAndAnalysis();

        TeslaDB.setDB();
        BaseActivity.setHelper(SwipeActivityHelper.class);
        if (activityLifecycleCallbacks == null){
            activityLifecycleCallbacks = new ActivityLifeCycleReportCallback();
        }
        registerActivityLifecycleCallbacks(activityLifecycleCallbacks);

    }


    private void setUpCrashAndAnalysis(){
        //本地crash日志收集
        CrashHandler.setupCrashHandler(getApplicationContext());
        //bugly统计
        CrashReport.initCrashReport(getApplicationContext(),BuildConfig.BUGLY_APP_ID,BuildConfig.LOG_DEBUG);


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
}
