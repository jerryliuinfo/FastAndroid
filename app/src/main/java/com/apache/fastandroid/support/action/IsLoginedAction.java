package com.apache.fastandroid.support.action;

import android.app.Activity;

import com.apache.fastandroid.artemis.ArtemisContext;
import com.apache.fastandroid.artemis.comBridge.ModularizationDelegate;
import com.apache.fastandroid.artemis.comBridge.ModuleConstans;
import com.tesla.framework.support.action.IAction;

/**
 * Created by 01370340 on 2017/11/18.
 */

public class IsLoginedAction extends IAction {
    public IsLoginedAction(Activity context, IAction parent) {
        super(context, parent);
    }

    @Override
    public boolean interrupt() {
        boolean interupt =  ArtemisContext.getUserBean() == null;
        if (interupt){
            doInterrupt();
        }
        return interupt;

    }

    @Override
    public void doInterrupt() {
        try {
            ModularizationDelegate.getInstance().runStaticAction(ModuleConstans.MODULE_USER_CENTER_NAME+":startLoginActivity",null,null,new Object[]{getContext()});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
