package com.apache.fastandroid.artemis;

import android.content.Context;

import com.apache.fastandroid.artemis.util.activitytask.ActivityLifeCallback;
import com.tesla.framework.applike.FrameworkApplication;

/**
 * Created by 01370340 on 2018/4/13.
 */

public class BaseApp {
    public static void onCreate(Context context){
        FrameworkApplication.onCreate(context);
        ActivityLifeCallback.registSelf(context);

    }

    public static String getFilePath(){
        return FrameworkApplication.getContext().getFilesDir().getAbsolutePath();
    }


    public static String getCachePath(){
        return FrameworkApplication.getContext().getCacheDir().getAbsolutePath();
    }


    public static String getExternalFilePath(){
        return FrameworkApplication.getContext().getExternalFilesDir(null).getAbsolutePath();
    }

    public static String getExternalCachePath(){
        return FrameworkApplication.getContext().getExternalCacheDir().getAbsolutePath();
    }
}
