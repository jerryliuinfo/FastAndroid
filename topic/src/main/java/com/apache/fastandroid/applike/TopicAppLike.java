package com.apache.fastandroid.applike;

import android.content.Context;

import com.apache.fastandroid.artemis.bridge.ModuleConstans;
import com.apache.fastandroid.delegate.TopicDelegateFactory;
import com.apache.fastandroid.topic.cache.TopicDb;
import com.tesla.framework.applike.DefApplicationLike;
import com.tesla.framework.component.bridge.ModularizationDelegate;

/**
 * Created by 01370340 on 2017/9/23.
 */

public class TopicAppLike extends DefApplicationLike {
    @Override
    public void onCreate(Context context) {
        ModularizationDelegate.getInstance().register(ModuleConstans.MODULE_TOPIC_NAME,new TopicDelegateFactory());
        TopicDb.initDb(context);
    }

    @Override
    public void onStop() {
        ModularizationDelegate.getInstance().unRegister(ModuleConstans.MODULE_TOPIC_NAME);
    }
}
