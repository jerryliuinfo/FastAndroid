package com.apache.fastandroid.user.delegate.impl;

import android.os.Bundle;

import com.apache.fastandroid.artemis.bridge.DelegateException;
import com.apache.fastandroid.artemis.bridge.IActionDelegate;
import com.apache.fastandroid.user.support.cache.UserCache;

/**
 * Created by 01370340 on 2017/9/3.
 */

public class ClearLoginInfo implements IActionDelegate {
    @Override
    public void runAction(Bundle args, IActionCallback callback, Object... extras) throws DelegateException {
        //UserConfigManager.getInstance().saveUserBean(null);
        UserCache.clearMe();
    }
}
