package com.apache.fastandroid.delegate.impl;

import android.app.Activity;
import android.os.Bundle;

import com.apache.fastandroid.MainActivity;
import com.tesla.framework.component.bridge.DelegateException;
import com.tesla.framework.component.bridge.IActionDelegate;

/**
 * Created by 01370340 on 2017/9/3.
 */

public class StartMainActivity implements IActionDelegate {
    @Override
    public void runAction(Bundle args, IActionCallback callback, Object... extras) throws DelegateException {
        if (extras != null && extras.length > 0 && extras[0] instanceof Activity){
            Activity from = (Activity) extras[0];
            MainActivity.launch(from);
        }
    }
}
