package com.apache.fastandroid.user.delegate.impl;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.tesla.framework.component.bridge.DelegateException;
import com.tesla.framework.component.bridge.IActionDelegate;
import com.apache.fastandroid.user.ui.LoginFragment;

/**
 * Created by 01370340 on 2017/9/3.
 */

public class StartLoginActivityWithNewTask implements IActionDelegate {
    @Override
    public void runAction(Bundle args, IActionCallback callback, Object... extras) throws DelegateException {
        if (extras != null && extras.length > 0 && extras[0] instanceof Context){
            Activity from = (Activity) extras[0];
            LoginFragment.start(from,false);
        }
    }
}
