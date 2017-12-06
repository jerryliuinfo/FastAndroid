package com.apache.fastandroid.user.delegate;

import com.apache.fastandroid.user.delegate.impl.DoLogin;
import com.apache.fastandroid.user.delegate.impl.GetToken;
import com.apache.fastandroid.user.delegate.impl.SaveToken;
import com.apache.fastandroid.user.delegate.impl.StartLoginActivity;
import com.apache.fastandroid.user.delegate.impl.StartLoginActivityWithNewTask;
import com.apache.fastandroid.user.delegate.impl.StartLoginedListActivity;
import com.tesla.framework.component.bridge.IActionDelegate;
import com.tesla.framework.component.bridge.IDataDelegate;
import com.tesla.framework.component.bridge.IDelegateFactory;
import com.tesla.framework.component.bridge.IObjectDataDelegate;

/**
 * Created by 01370340 on 2017/9/3.
 */

public class UsercenterDelegateFactory implements IDelegateFactory {
    public static final String ACTION_START_LOGINACTIVITY = "startLoginActivity";

    public static final String ACTION_START_LOGINED_USER_LIST_ACTIVITY = "startLoginedUserListActivity";


    public static final String ACTION_START_LOGINACTIVITY_NEWTASK = "startLoginActivityNewTask";
    public static final String ACTION_DOLOGIN = "doLogin";
    public static final String ACTION_GET_TOKEN = "getToken";
    public static final String ACTION_SAVE_TOKEN = "saveToken";

    @Override
    public IDataDelegate getDataTransfer(String action) {
        switch (action){
            case ACTION_GET_TOKEN:
                return new GetToken();

        }
        return null;
    }

    @Override
    public IActionDelegate getActionTransfer(String action) {
        switch (action){
            case ACTION_START_LOGINACTIVITY:
                return new StartLoginActivity();
            case ACTION_START_LOGINED_USER_LIST_ACTIVITY:
                return new StartLoginedListActivity();
            case ACTION_START_LOGINACTIVITY_NEWTASK:
                return new StartLoginActivityWithNewTask();

            case ACTION_SAVE_TOKEN:
                return new SaveToken();
            case ACTION_DOLOGIN:
                return new DoLogin();

        }
        return null;
    }

    @Override
    public IObjectDataDelegate getObjectDataTransfer(String action) {
        switch (action){

        }
        return null;
    }
}
