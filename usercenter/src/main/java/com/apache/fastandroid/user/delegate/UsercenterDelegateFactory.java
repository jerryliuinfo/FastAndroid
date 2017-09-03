package com.apache.fastandroid.user.delegate;

import com.apache.fastandroid.artemis.comBridge.IActionDelegate;
import com.apache.fastandroid.artemis.comBridge.IDataDelegate;
import com.apache.fastandroid.artemis.comBridge.IDelegateFactory;
import com.apache.fastandroid.artemis.comBridge.IObjectDataDelegate;
import com.apache.fastandroid.user.delegate.impl.AutoLogin;
import com.apache.fastandroid.user.delegate.impl.StartLoginActivity;
import com.apache.fastandroid.user.delegate.impl.StartLoginActivityWithNewTask;

/**
 * Created by 01370340 on 2017/9/3.
 */

public class UsercenterDelegateFactory implements IDelegateFactory {
    public static final String ACTION_START_LOGINACTIVITY = "startLoginActivity";
    public static final String ACTION_START_LOGINACTIVITY_NEWTASK = "startLoginActivityNewTask";
    public static final String ACTION_AUTO_LOGIN = "autoLogin";
    @Override
    public IDataDelegate getDataTransfer(String action) {
        return null;
    }

    @Override
    public IActionDelegate getActionTransfer(String action) {
        switch (action){
            case ACTION_START_LOGINACTIVITY:
                return new StartLoginActivity();
            case ACTION_START_LOGINACTIVITY_NEWTASK:
                return new StartLoginActivityWithNewTask();
            case ACTION_AUTO_LOGIN:
                return new AutoLogin();
        }
        return null;
    }

    @Override
    public IObjectDataDelegate getObjectDataTransfer(String action) {
        return null;
    }
}
