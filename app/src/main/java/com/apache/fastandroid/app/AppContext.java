package com.apache.fastandroid.app;

import android.content.Context;

import com.apache.fastandroid.artemis.ArtemisContext;
import com.apache.fastandroid.artemis.CacheUtil;
import com.apache.fastandroid.artemis.comBridge.ModularizationDelegate;
import com.apache.fastandroid.artemis.comBridge.ModuleConstans;
import com.apache.fastandroid.artemis.support.bean.Token;
import com.apache.fastandroid.artemis.support.bean.UserDetail;
import com.tesla.framework.common.util.ActivityTaskMgr;

/**
 * Created by 01370340 on 2017/9/2.
 */

public class AppContext {


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

    public static void login(Token token){

    }


}
