package com.apache.fastandroid.app;

import android.content.Context;

import com.apache.fastandroid.artemis.comBridge.ModularizationDelegate;
import com.apache.fastandroid.artemis.support.bean.Token;
import com.apache.fastandroid.artemis.support.bean.UserBean;
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


    public static void logout(Context context){
        mUserBean = null;
        ActivityTaskMgr.getInstance().clearActivityStack();
        try {
            ModularizationDelegate.getInstance().runStaticAction("com.apache.fastandroid:userCenter:ClearLoginInfo",null,null,new Object[]{});
            ModularizationDelegate.getInstance().runStaticAction("com.apache.fastandroid:userCenter:startLoginActivityNewTask",null,null,new Object[]{context});

            //LoginFragment.start(MyApplication.getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }



    public static void login(UserBean userBean){
        setUserBean(userBean);
    }

    public static void login(Token token){

    }


}
