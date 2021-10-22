package com.apache.fastandroid.artemis;

import android.app.Application;
import android.content.Context;

/**
 * Created by 01370340 on 2018/4/13.
 */

public class BaseApp {
    private static Context sContext;

    private static Application sApplication;

    /**
     * 获取全局 Application
     */
    public static Application getApplication() {
        return sApplication;
    }






}
