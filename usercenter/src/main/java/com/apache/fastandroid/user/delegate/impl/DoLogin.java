package com.apache.fastandroid.user.delegate.impl;

import android.os.Bundle;

import com.tesla.framework.component.bridge.DelegateException;
import com.tesla.framework.component.bridge.IActionDelegate;
import com.apache.fastandroid.user.delegate.LoginTask;

/**
 * Created by 01370340 on 2017/9/3.
 */

public class DoLogin implements IActionDelegate {
    @Override
    public void runAction(Bundle args, final IActionCallback callback, Object... extras) throws DelegateException {
        new LoginTask().doAutoLogin(callback);


    }


}
