package com.apache.fastandroid;

import android.app.Application;

import com.tesla.framework.FrameworkApplication;
import com.tesla.framework.support.db.TeslaDB;

/**
 * Created by jerryliu on 2017/3/26.
 */

public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        FrameworkApplication.onCreate(getApplicationContext());
        TeslaDB.setDB();

    }




}
