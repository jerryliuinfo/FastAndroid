package com.apache.fastandroid.artemis;

import android.content.Context;

import com.apache.fastandroid.artemis.support.bean.UserDetail;
import com.tesla.framework.common.util.activitytask.ActivityTaskMgr;

/**
 * Created by 01370340 on 2017/9/2.
 */

public class AppContext {
    private static UserDetail mUserBean;

    public static void logout(Context context){
        ArtemisContext.setUserBean(null);
        ActivityTaskMgr.getInstance().clearActivityStack();
        CacheUtil.clearToken();
    }



    public static void login(UserDetail userBean){
        ArtemisContext.setUserBean(userBean);
    }


    public static boolean isLogined(){
        return mUserBean != null;
    }

    public static boolean isNotLogined(){
        return !isLogined();
    }


}
