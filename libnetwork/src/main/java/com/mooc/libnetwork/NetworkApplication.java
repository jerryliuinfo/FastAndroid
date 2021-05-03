package com.mooc.libnetwork;

import android.app.Application;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Jerry on 2021/5/3.
 */
public class NetworkApplication {

    private static Application sApplication;

    static {
        try {
            Class<?> activityThread = Class.forName("android.app.ActivityThread");
            Method m_currentActivityThread = activityThread.getDeclaredMethod("currentActivityThread");
            Field f_mInitialApplication = activityThread.getDeclaredField("mInitialApplication");
            f_mInitialApplication.setAccessible(true);
            Object current = m_currentActivityThread.invoke(null);
            Object app = f_mInitialApplication.get(current);
            sApplication = (Application) app;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取全局 Application
     */
    public static Application getApplication() {
        return sApplication;
    }
}
