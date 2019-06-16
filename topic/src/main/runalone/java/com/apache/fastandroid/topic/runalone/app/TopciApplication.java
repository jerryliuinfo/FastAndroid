package com.apache.fastandroid.topic.runalone.app;

import android.app.Application;
import com.apache.fastandroid.artemis.BaseApp;

/**
 * author: 01370340
 * data: 2019/6/14
 * description:
 */
public class TopciApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        BaseApp.onCreate(this);
    }
}
