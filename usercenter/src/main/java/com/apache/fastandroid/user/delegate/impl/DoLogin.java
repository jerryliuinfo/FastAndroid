package com.apache.fastandroid.user.delegate.impl;

import android.app.Activity;
import android.os.Bundle;

import com.tesla.framework.component.bridge.DelegateException;
import com.tesla.framework.component.bridge.IActionDelegate;
import com.apache.fastandroid.user.delegate.LoginTask;

/**
 * Created by 01370340 on 2017/9/3.
 * 执行登录操作,至于是自动登录还是跳转到登录界面手动登录，由UserCenter内部控制
 */

public class DoLogin implements IActionDelegate {
    @Override
    public void runAction(Bundle args, final IActionCallback callback, Object... extras) throws DelegateException {
        if (extras != null && extras.length > 0 && extras[0] instanceof Activity){
            Activity activity = (Activity) extras[0];
            new LoginTask().doAutoLogin(activity,callback);
        }
    }


}
