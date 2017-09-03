package com.apache.fastandroid.delegate.impl;

import android.content.Context;
import android.os.Bundle;

import com.apache.fastandroid.app.AppContext;
import com.apache.fastandroid.artemis.comBridge.DelegateException;
import com.apache.fastandroid.artemis.comBridge.IActionDelegate;

/**
 * Created by 01370340 on 2017/9/3.
 */

public class Logout implements IActionDelegate {
    @Override
    public void runAction(Bundle args, IActionCallback callback, Object... extras) throws DelegateException {
        if (extras != null && extras.length > 0 && extras[0] instanceof Context){
            AppContext.logout((Context) extras[0]);
        }

    }
}
