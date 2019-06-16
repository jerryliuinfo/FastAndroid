package com.apache.fastandroid.artemis.util.activitytask;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import java.lang.ref.WeakReference;
import java.util.LinkedList;

/**
 * Created by 01370340 on 2018/4/4.
 * 可以对activity生命周期方法内做一些业务处理，例如埋点
 */

public class ActivityLifeCallback implements Application.ActivityLifecycleCallbacks {
    private static ActivityLifeCallback sInstance = new ActivityLifeCallback();
    private static Context  mContext;

    private LinkedList<WeakReference<Activity>> activityList = new LinkedList<>();

    public static ActivityLifeCallback get(){
        return sInstance;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
    }


    public static void registSelf(Context context){
        Application application = (Application) context.getApplicationContext();
        application.registerActivityLifecycleCallbacks(sInstance);
        mContext = application.getApplicationContext();
    }






}
