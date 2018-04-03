package com.tesla.framework.common.util;

import android.content.Context;

/**
 * Created by 01370340 on 2017/12/23.
 */

public class ContextUtil {
    private static Context sContext;
    public static void injectContext(Context context){
        sContext = context;
    }

    public static Context getContext(){
        return sContext;
    }
}
