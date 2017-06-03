package com.apache.fastandroid;

import android.app.Application;
import android.content.Context;

import com.apache.fastandroid.support.report.ActivityLifeCycleReportCallback;
import com.tesla.framework.FrameworkApplication;
import com.tesla.framework.common.util.log.Logger;
import com.tesla.framework.common.util.log.NLog;
import com.tesla.framework.support.db.TeslaDB;
import com.tesla.framework.ui.activity.BaseActivity;
import com.tesla.framework.ui.widget.swipeback.SwipeActivityHelper;

/**
 * Created by jerryliu on 2017/3/26.
 */

public class MyApplication extends Application{
    public static final String TAG = MyApplication.class.getSimpleName();
    private static Context mContext;
    private ActivityLifecycleCallbacks activityLifecycleCallbacks;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        NLog.setDebug(true, Logger.DEBUG);
        FrameworkApplication.onCreate(getApplicationContext());

        TeslaDB.setDB();
        BaseActivity.setHelper(SwipeActivityHelper.class);
        if (activityLifecycleCallbacks == null){
            activityLifecycleCallbacks = new ActivityLifeCycleReportCallback();
        }
        registerActivityLifecycleCallbacks(activityLifecycleCallbacks);

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
