package com.apache.fastandroid.artemis;

import android.app.Application;
import android.content.Context;

import com.blankj.utilcode.util.ktx.SPreference;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

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

    /**
     *
     /**
     * 这种方式获取全局的Application 是一种拓展思路。
     * <p>
     * 对于组件化项目,不可能把项目实际的Application下沉到Base,而且各个module也不需要知道Application真实名字
     * <p>
     * 这种一次反射就能获取全局Application对象的方式相比于在Application#OnCreate保存一份的方式显示更加通用了
     */

    static {
        try {
            Class<?> activityThread = Class.forName("android.app.ActivityThread");
            Method m_currentActivityThread = activityThread.getDeclaredMethod("currentActivityThread");
            Field f_mInitialApplication = activityThread.getDeclaredField("mInitialApplication");
            f_mInitialApplication.setAccessible(true);
            Object current = m_currentActivityThread.invoke(null);
            Object app = f_mInitialApplication.get(current);
            sApplication = (Application) app;

            sContext = sApplication.getApplicationContext();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public static void onCreate(Context context){
        SPreference.setContext(context);

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




}
