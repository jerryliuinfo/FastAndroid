package com.apache.fastandroid.base;

import com.apache.fastandroid.app.MyApplication;
import com.apache.fastandroid.user.LoginFragment;
import com.apache.fastandroid.user.UserConfigManager;
import com.apache.fastandroid.user.bean.UserBean;
import com.tesla.framework.common.util.ActivityTaskMgr;

/**
 * Created by 01370340 on 2017/9/2.
 */

public class AppContext {
    private static UserBean mUserBean;

    public static void setUserBean(UserBean userBean){
        mUserBean =  userBean;
    }

    public static UserBean getUserBean(){
        return mUserBean;
    }


    public static void logout(){
        mUserBean = null;

        UserConfigManager.getInstance().saveUserBean(null);
        ActivityTaskMgr.getInstance().clearActivityStack();
        LoginFragment.start(MyApplication.getContext());
    }

    public static void login(UserBean userBean){
        setUserBean(userBean);
    }

}
