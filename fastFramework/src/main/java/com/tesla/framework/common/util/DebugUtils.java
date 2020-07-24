package com.tesla.framework.common.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Debug;


public class DebugUtils {

    private DebugUtils(){}

    private static Boolean isDebug = null;

    /**
     * debug版本时，返回true， release版本返回false
     * @return
     */
    public static Boolean isDebugVersion(){
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

    /**
     * 是否正在执行debug调试
     * @return
     */
    public static boolean isExecutingDebug(){
        return Debug.isDebuggerConnected() || Debug.waitingForDebugger();
    }



}
