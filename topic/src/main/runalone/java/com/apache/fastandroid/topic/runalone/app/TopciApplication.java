package com.apache.fastandroid.topic.runalone.app;

import android.app.Application;
import com.apache.fastandroid.artemis.BaseApp;
import com.apache.fastandroid.topic.support.TopicLogUtil;
import com.tesla.framework.common.util.log.Logger;
import com.tesla.framework.common.util.log.NLog;
import com.tesla.framework.route.Route;

/**
 * author: 01370340
 * data: 2019/6/14
 * description:
 */
public class TopciApplication extends Application  {
    @Override
    public void onCreate() {
        super.onCreate();
        NLog.setDebug(true, Logger.DEBUG);
        BaseApp.onCreate(this);
        TopicLogUtil.d("TopciApplication onCreate");
        Route.registerComponent("com.apache.fastandroid.topic.app.TopicApplicationLike", this);

    }



}
