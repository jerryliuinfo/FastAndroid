package com.apache.fastandroid.delegate.impl;

import android.content.Context;
import android.os.Bundle;

import com.apache.fastandroid.app.AppContext;
import com.apache.fastandroid.artemis.bridge.DelegateException;
import com.apache.fastandroid.artemis.bridge.IActionDelegate;

/**
 * Created by 01370340 on 2017/9/3.
 * 退出登录后 做一些业务处理，例如清空登录信息,跳转到登录界面等
 */

public class Logout implements IActionDelegate {
    @Override
    public void runAction(Bundle args, IActionCallback callback, Object... extras) throws DelegateException {
        if (extras != null && extras.length > 0 && extras[0] instanceof Context){
            AppContext.logout((Context) extras[0]);
        }

    }
}
