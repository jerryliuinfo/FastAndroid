package com.tesla.framework.common.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Debug;

import com.tesla.framework.applike.FApplication;

/**
 * @author shunyou.huang
 * @Description:
 * @date 2017/3/22 10:35
 * @copyright TCL-MIG
 */


public class DebugUtils {

    //TODO 测试代码不要提交
    private static boolean isTest = false;

    private DebugUtils(){}

    public static  Boolean isTest(){
        return isTest;
    }



    private static Boolean isDebug = null;

    public static synchronized Boolean isDebug(){
        if (isDebug == null){
            syncDebugStatus(FApplication.getContext());
        }
        return isDebug != null && isDebug.booleanValue();
    }

    public static void syncDebugStatus(Context context){
        if (context == null || context.getApplicationContext() == null){
            return;
        }

        if (isDebug == null){
            ApplicationInfo appInfo = context.getApplicationInfo();
            if (appInfo != null){
                isDebug = (appInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
            }
        }
    }


    /**
     * 当前是否正在debug
     * @return
     */
    public static boolean isDebugConnected(){
        return Debug.isDebuggerConnected() || Debug.waitingForDebugger();
    }

}
