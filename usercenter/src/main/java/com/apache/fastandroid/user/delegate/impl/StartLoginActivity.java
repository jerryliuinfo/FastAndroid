package com.apache.fastandroid.user.delegate.impl;

import android.app.Activity;
import android.os.Bundle;

import com.apache.fastandroid.user.ui.LoginFragmentMvp;
import com.tesla.framework.component.bridge.DelegateException;
import com.tesla.framework.component.bridge.IActionDelegate;

/**
 * Created by 01370340 on 2017/9/3.
 */

public class StartLoginActivity implements IActionDelegate {
    @Override
    public void runAction(Bundle args, IActionCallback callback, Object... extras) throws DelegateException {
        if (extras != null && extras.length > 0 && extras[0] instanceof Activity){
            Activity from = (Activity) extras[0];
            if (extras.length >= 2){
                LoginFragmentMvp.start(from, (Boolean) extras[1]);
            }else {
                LoginFragmentMvp.start(from,false);
            }

        }
    }
}
