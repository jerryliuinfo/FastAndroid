package com.apache.fastandroid;

import android.app.Application;
import android.content.Context;

import com.tesla.framework.FrameworkApplication;
import com.tesla.framework.support.db.TeslaDB;
import com.tesla.framework.ui.activity.BaseActivity;
import com.tesla.framework.ui.widget.swipeback.SwipeActivityHelper;

/**
 * Created by jerryliu on 2017/3/26.
 */

public class MyApplication extends Application{
    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        FrameworkApplication.onCreate(getApplicationContext());
        TeslaDB.setDB();
        BaseActivity.setHelper(SwipeActivityHelper.class);


    }

    public static Context getContext(){
        return mContext;
    }


}
