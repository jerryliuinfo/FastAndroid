package com.apache.fastandroid.delegate.impl;

import android.os.Bundle;

import com.apache.fastandroid.app.AppContext;
import com.tesla.framework.component.bridge.DelegateException;
import com.tesla.framework.component.bridge.IActionDelegate;
import com.apache.fastandroid.artemis.support.bean.UserDetail;

/**
 * Created by 01370340 on 2017/9/3.
 * 登录成功后,通知主模块,做一些业务处理
 */

public class LoginSuccess implements IActionDelegate {
    @Override
    public void runAction(Bundle args, IActionCallback callback, Object... extras) throws DelegateException {
        if (args != null){
            UserDetail bean = (UserDetail) args.getSerializable("result");
            AppContext.login(bean);
        }

    }
}