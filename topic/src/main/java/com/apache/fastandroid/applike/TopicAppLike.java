package com.apache.fastandroid.applike;

import com.apache.fastandroid.artemis.comBridge.IApplicationLike;
import com.apache.fastandroid.artemis.comBridge.ModularizationDelegate;
import com.apache.fastandroid.artemis.comBridge.ModuleConstans;
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
