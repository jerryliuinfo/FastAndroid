package com.tesla.framework.applike;

import android.app.Application;
import android.content.Context;

import com.tesla.framework.kt.SPreference;
import com.tesla.framework.common.util.log.NLog;
import com.tesla.framework.common.util.network.NetworkHelper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by jerryliu on 2017/3/26.
 */

public class FApplication  {
    public static final String TAG ="FApplication";
    private static Context sContext;

    private static Application sApplication;


    public static void onCreate(Application application) {
        NLog.d(TAG, "FApplication onCreate");
        sApplication = application;
        sContext = application.getApplicationContext();
        SPreference.setContext(sContext);
        NetworkHelper.getInstance().registerNetworkSensor(sContext);

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
            NLog.e(TAG, "FApplication static block execute");

            Class<?> activityThread = Class.forName("android.app.ActivityThread");
            Method m_currentActivityThread = activityThread.getDeclaredMethod("currentActivityThread");
            Field f_mInitialApplication = activityThread.getDeclaredField("mInitialApplication");
            f_mInitialApplication.setAccessible(true);
            Object current = m_currentActivityThread.invoke(null);
            Object app = f_mInitialApplication.get(current);
            sApplication = (Application) app;

            sContext = sApplication.getApplicationContext();

            onCreate(sApplication);
        } catch (Exception e) {
            NLog.e(TAG, "FApplication static block reflect error: %s",e.getMessage());
            e.printStackTrace();
        }
    }



    public static Context getContext(){
        checkContext();
        return sContext;
    }

    public static Application getApplication(){
        return sApplication;
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

    private static void checkContext(){
        if (sContext == null){
            throw new NullPointerException("must call onCreate() before call this method");
        }
    }


}
