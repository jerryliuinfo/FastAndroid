package com.apache.fastandroid.app;

import android.content.Context;
import com.apache.fastandroid.artemis.ArtemisContext;
import com.apache.fastandroid.artemis.CacheUtil;
import com.apache.fastandroid.artemis.bridge.ModuleConstans;
import com.apache.fastandroid.artemis.support.bean.UserDetail;
import com.tesla.framework.common.util.activitytask.ActivityTaskMgr;
import com.tesla.framework.component.bridge.ModularizationDelegate;

/**
 * Created by 01370340 on 2017/9/2.
 */

public class AppContext {
    private static UserDetail mUserBean;

    public static void logout(Context context){
        ArtemisContext.setUserBean(null);
        ActivityTaskMgr.getInstance().clearActivityStack();
        CacheUtil.clearToken();
        try {
            ModularizationDelegate.getInstance().runStaticAction(ModuleConstans.MODULE_USER_CENTER_NAME+":ClearLoginInfo",null,null,new Object[]{});
            ModularizationDelegate.getInstance().runStaticAction(ModuleConstans.MODULE_USER_CENTER_NAME+":userCenter:startLoginActivityNewTask",null,null,new Object[]{context});

        } catch (Exception e) {
            e.printStackTrace();
        }


    }



    public static void login(UserDetail userBean){
        ArtemisContext.setUserBean(userBean);
    }



}
