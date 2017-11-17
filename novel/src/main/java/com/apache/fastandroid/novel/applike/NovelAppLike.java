package com.apache.fastandroid.novel.applike;

import com.apache.fastandroid.artemis.comBridge.IApplicationLike;
import com.apache.fastandroid.artemis.comBridge.ModularizationDelegate;
import com.apache.fastandroid.artemis.comBridge.ModuleConstans;
import com.apache.fastandroid.novel.delegate.NovelDelegateFactory;
import com.tesla.framework.FrameworkApplication;
import com.tesla.framework.common.setting.SettingUtility;

/**
 * Created by 01370340 on 2017/9/24.
 */

public class NovelAppLike implements IApplicationLike {
    @Override
    public void onCreate() {
        ModularizationDelegate.getInstance().register(ModuleConstans.MODULE_NOVEL_NAME,new NovelDelegateFactory());
        // 添加一些配置项
        SettingUtility.addSettings(FrameworkApplication.getContext(), "actions");
    }

    @Override
    public void onStop() {
        ModularizationDelegate.getInstance().unRegister(ModuleConstans.MODULE_NOVEL_NAME);
    }
}
