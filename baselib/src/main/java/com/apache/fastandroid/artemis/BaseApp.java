package com.apache.fastandroid.artemis;

import android.content.Context;

import com.apache.fastandroid.artemis.util.activitytask.ActivityLifeCallback;
import com.tesla.framework.applike.FrameworkApplication;

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
