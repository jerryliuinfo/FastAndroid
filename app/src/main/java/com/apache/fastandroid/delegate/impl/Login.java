package com.apache.fastandroid.delegate.impl;

import android.os.Bundle;

import com.apache.fastandroid.app.AppContext;
import com.apache.fastandroid.artemis.comBridge.DelegateException;
import com.apache.fastandroid.artemis.comBridge.IActionDelegate;
import com.apache.fastandroid.artemis.support.bean.UserBean;

/**
 * Created by 01370340 on 2017/9/3.
 */

public class Login implements IActionDelegate {
    @Override
    public void runAction(Bundle args, IActionCallback callback, Object... extras) throws DelegateException {
        if (args != null){
            UserBean bean = (UserBean) args.getSerializable("result");
            AppContext.login(bean);
        }

    }
}
