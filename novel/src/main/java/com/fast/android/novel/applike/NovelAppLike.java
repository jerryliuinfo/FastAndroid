package com.fast.android.novel.applike;

import com.apache.fastandroid.artemis.comBridge.IApplicationLike;
import com.apache.fastandroid.artemis.comBridge.ModularizationDelegate;
import com.apache.fastandroid.artemis.comBridge.ModuleConstans;
import com.fast.android.novel.delegate.NovelDelegateFactory;

/**
 * Created by 01370340 on 2017/9/24.
 */

public class NovelAppLike implements IApplicationLike {
    @Override
    public void onCreate() {
        ModularizationDelegate.getInstance().register(ModuleConstans.MODULE_NOVEL_NAME,new NovelDelegateFactory());
    }

    @Override
    public void onStop() {
        ModularizationDelegate.getInstance().unRegister(ModuleConstans.MODULE_NOVEL_NAME);
    }
}
