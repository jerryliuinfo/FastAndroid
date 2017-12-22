package com.apache.fastandroid.novel.applike;

import android.content.Context;

import com.apache.fastandroid.artemis.bridge.ModuleConstans;
import com.tesla.framework.applike.IApplicationLike;
import com.tesla.framework.component.bridge.ModularizationDelegate;

/**
 * Created by 01370340 on 2017/9/24.
 */

public class NovelAppLike implements IApplicationLike {
    @Override
    public void onCreate(Context context) {
        //ModularizationDelegate.getInstance().register(ModuleConstans.MODULE_NOVEL_NAME,new NovelDelegateFactory());
    }

    @Override
    public void onStop() {
        ModularizationDelegate.getInstance().unRegister(ModuleConstans.MODULE_NOVEL_NAME);
    }
}
