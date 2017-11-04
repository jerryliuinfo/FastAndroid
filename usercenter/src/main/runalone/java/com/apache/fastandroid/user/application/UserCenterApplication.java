package com.apache.fastandroid.user.application;

import android.app.Application;

import com.tesla.framework.FrameworkApplication;

/**
 * Created by mrzhang on 2017/8/16.
 */

public class UserCenterApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FrameworkApplication.onCreate(getApplicationContext());
    }

}