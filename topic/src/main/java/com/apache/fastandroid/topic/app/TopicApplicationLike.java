package com.apache.fastandroid.topic.app;

import android.content.Context;
import com.apache.fastandroid.artemis.componentService.topic.ITopicService;
import com.apache.fastandroid.topic.serviceimpl.TopicServiceImpl;
import com.apache.fastandroid.topic.support.TopicLogUtil;
import com.tesla.framework.applike.IApplicationLike;
import com.tesla.framework.route.Route;

/**
 * author: 01370340
 * data: 2019/6/17
 * description:
 */
public class TopicApplicationLike implements IApplicationLike {
    Route mRoute = Route.getInstance();
    @Override
    public void onCreate(Context context) {
        TopicLogUtil.d("TopicApplicationLike onCreate");
        mRoute.addService(ITopicService.class.getSimpleName(), new TopicServiceImpl());
    }

    @Override
    public void onStop() {
        mRoute.removeService(ITopicService.class.getSimpleName());
    }

    @Override
    public void onTrimMemory() {

    }
}
