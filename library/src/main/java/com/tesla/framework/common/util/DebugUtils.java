package com.tesla.framework.common.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;



public class DebugUtils {

    private DebugUtils(){}

    private static Boolean isDebug = null;

    /**
     * debug版本时，返回true， release版本返回false
     * @return
     */
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
