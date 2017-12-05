package com.apache.fastandroid.applike;

import com.tesla.framework.applike.IApplicationLike;
import com.tesla.framework.component.bridge.ModularizationDelegate;
import com.apache.fastandroid.artemis.bridge.ModuleConstans;
import com.apache.fastandroid.delegate.MainDelegateFactory;

/**
 * Created by 01370340 on 2017/9/23.
 */

public class MainAppLike implements IApplicationLike {
    @Override
    public void onCreate() {
        ModularizationDelegate.getInstance().register(ModuleConstans.MODULE_MAIN_NAME,new MainDelegateFactory());

    }

    @Override
    public void onStop() {
        ModularizationDelegate.getInstance().unRegister(ModuleConstans.MODULE_MAIN_NAME);
    }
}
