package com.apache.fastandroid.user.applike;

import com.apache.fastandroid.artemis.comBridge.IApplicationLike;
import com.apache.fastandroid.artemis.comBridge.ModularizationDelegate;
import com.apache.fastandroid.user.delegate.UsercenterDelegateFactory;

/**
 * Created by 01370340 on 2017/9/23.
 */

public class UserCenterAppLike implements IApplicationLike {
    @Override
    public void onCreate() {
        ModularizationDelegate.getInstance().register("com.apache.fastandroid:userCenter",new UsercenterDelegateFactory());

    }

    @Override
    public void onStop() {
        ModularizationDelegate.getInstance().unRegister("com.apache.fastandroid:userCenter");
    }
}
