package com.apache.fastandroid.user.delegate.impl;

import android.os.Bundle;

import com.apache.fastandroid.artemis.comBridge.DelegateException;
import com.apache.fastandroid.artemis.comBridge.IActionDelegate;
import com.apache.fastandroid.user.UserSDK;
import com.tesla.framework.network.task.TaskException;

/**
 * Created by 01370340 on 2017/9/3.
 */

public class AutoLogin implements IActionDelegate {
    @Override
    public void runAction(Bundle args, IActionCallback callback, Object... extras) throws DelegateException {
        try {
            UserSDK.newInstance().doAutoLogin(callback);
        } catch (TaskException e) {
            e.printStackTrace();
        }
    }
}
