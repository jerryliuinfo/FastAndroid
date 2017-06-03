package com.tesla.framework.common.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;

/**
 * @author shunyou.huang
 * @Description:
 * @date 2017/3/22 10:35
 * @copyright TCL-MIG
 */


public class DebugUtils {

    private DebugUtils(){}

    private static Boolean isDebug = null;

    public static Boolean isDebug(){
        return isDebug == null ? false : isDebug.booleanValue();
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

}
