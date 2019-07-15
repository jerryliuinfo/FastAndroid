package com.apache.fastandroid.artemis;

import android.content.Context;

import com.apache.fastandroid.artemis.support.bean.UserInfoBean;
import com.tesla.framework.common.util.activitytask.ActivityTaskMgr;

/**
 * Created by 01370340 on 2017/9/2.
 */

public class AppContext {
    private static UserInfoBean mUserBean;

    public static void logout(Context context){
        mUserBean = null;
        ActivityTaskMgr.getInstance().clearActivityStack();
        CacheUtil.clearToken();
    }


    public static String getUserName(){
        return mUserBean != null ? mUserBean.getUserName() : "";
    }



    public static void login(UserInfoBean userBean){
        mUserBean = userBean;
    }


    public static boolean isLogined(){
        return mUserBean != null;
    }

    public static UserInfoBean getUserInfoBean(){
        return mUserBean;
    }


    public static boolean isNotLogined(){
        return !isLogined();
    }


}
