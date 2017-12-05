package com.apache.fastandroid.applike;

import com.apache.fastandroid.artemis.applike.IApplicationLike;
import com.apache.fastandroid.artemis.bridge.ModularizationDelegate;
import com.apache.fastandroid.artemis.bridge.ModuleConstans;
import com.apache.fastandroid.delegate.TopicDelegateFactory;

/**
 * Created by 01370340 on 2017/9/23.
 */

public class TopicAppLike implements IApplicationLike {
    @Override
    public void onCreate() {
        ModularizationDelegate.getInstance().register(ModuleConstans.MODULE_TOPIC_NAME,new TopicDelegateFactory());
    }

    @Override
    public void onStop() {
        ModularizationDelegate.getInstance().unRegister(ModuleConstans.MODULE_TOPIC_NAME);
    }
}
