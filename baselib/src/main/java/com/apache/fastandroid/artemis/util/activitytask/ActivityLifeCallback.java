package com.apache.fastandroid.artemis.util.activitytask;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.tesla.framework.LibraryLog;

import java.util.LinkedList;

/**
 * Created by 01370340 on 2018/4/4.
 * 可以对activity生命周期方法内做一些业务处理，例如埋点
 */

public class ActivityLifeCallback implements Application.ActivityLifecycleCallbacks {
    private static ActivityLifeCallback sInstance = new ActivityLifeCallback();
    private Context mContext;

    private LinkedList<Activity> stack = new LinkedList<>();

    public static ActivityLifeCallback get(){
        return sInstance;
    }


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        LibraryLog.d("onActivityCreated activity name = %s",activity.getClass().getName());
        if (!stack.contains(activity)){
            stack.add(activity);
        }
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
        LibraryLog.d("onActivityDestroyed activity name = %s",activity.getClass().getName());

        if (stack.contains(activity)){
            stack.remove(activity);
        }
    }

    public Activity topActivity(){
        if (!stack.isEmpty()){
            return stack.getLast();
        }
        return null;
    }

    public void registSelf(Context context){
        Application application = (Application) context.getApplicationContext();
        application.registerActivityLifecycleCallbacks(sInstance);
        mContext = application.getApplicationContext();
    }
}
