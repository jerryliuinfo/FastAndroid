package com.apache.fastandroid.delegate;

import com.apache.fastandroid.artemis.comBridge.IActionDelegate;
import com.apache.fastandroid.artemis.comBridge.IDataDelegate;
import com.apache.fastandroid.artemis.comBridge.IDelegateFactory;
import com.apache.fastandroid.artemis.comBridge.IObjectDataDelegate;
import com.apache.fastandroid.delegate.impl.Login;
import com.apache.fastandroid.delegate.impl.Logout;
import com.apache.fastandroid.delegate.impl.StartMainActivity;

/**
 * Created by 01370340 on 2017/9/3.
 */

public class MainDelegateFactory implements IDelegateFactory {
    public static final String ACTION_START_MAINACTIVITY = "startMainActivity";
    public static final String ACTION_LOGIN= "login";
    public static final String ACTION_LOGOUT= "logout";
    @Override
    public IDataDelegate getDataTransfer(String action) {
        return null;
    }

    @Override
    public IActionDelegate getActionTransfer(String action) {
        switch (action){
            case ACTION_START_MAINACTIVITY:
                return new StartMainActivity();
            case ACTION_LOGIN:
                return new Login();
            case ACTION_LOGOUT:
                return new Logout();
        }
        return null;
    }

    @Override
    public IObjectDataDelegate getObjectDataTransfer(String action) {
        return null;
    }
}
