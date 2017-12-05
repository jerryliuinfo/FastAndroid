package com.apache.fastandroid.user.applike;

import com.apache.fastandroid.artemis.bridge.ModuleConstans;
import com.tesla.framework.applike.IApplicationLike;
import com.tesla.framework.component.bridge.ModularizationDelegate;

/**
 * Created by 01370340 on 2017/9/23.
 */

public class UserCenterAppLike implements IApplicationLike {
    @Override
    public void onCreate() {
        //ModularizationDelegate.getInstance().register(ModuleConstans.MODULE_USER_CENTER_NAME,new UsercenterDelegateFactory());

    }

    @Override
    public void onStop() {
        ModularizationDelegate.getInstance().unRegister(ModuleConstans.MODULE_USER_CENTER_NAME);
    }
}
