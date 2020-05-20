package com.apache.fastandroid.artemis;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.apache.fastandroid.artemis.util.activitytask.ActivityLifeCallback;
import com.tesla.framework.applike.FrameworkApplication;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by 01370340 on 2018/4/13.
 */

public class BaseApp {
    private static Context sContext;
    public static void onCreate(Context context){
        FrameworkApplication.onCreate(context);
        ActivityLifeCallback.registSelf(context);

        sContext = context.getApplicationContext();
    }

    private static void checkContext(){
        if (sContext == null){
            throw new NullPointerException("must call onCreate() before call this method");
        }
    }


    public static Context getContext(){
        return sContext;
    }

    // /data/data/com.sgs.unite/files
    public static String getFilePath(){
        checkContext();
        return sContext.getFilesDir().getAbsolutePath();
    }

    // /data/data/com.sgs.unite/cache
    public static String getCachePath(){
        checkContext();
        return sContext.getCacheDir().getAbsolutePath();
    }

    //Android/data/com.sgs.unite/files
    public static String getExternalFilePath(){
        checkContext();
        return sContext.getExternalFilesDir(null).getAbsolutePath();
    }

    //Android/data/com.sgs.unite/cache
    public static String getExternalCachePath(){
        checkContext();
        return sContext.getExternalCacheDir().getAbsolutePath();
    }

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

    /**
     * 设置全局 Application
     */
    public static void setupApplication(@NonNull Application application) {
        sApplication = application;
    }
}
