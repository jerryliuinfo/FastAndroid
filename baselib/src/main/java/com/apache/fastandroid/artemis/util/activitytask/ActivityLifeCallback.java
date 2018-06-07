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
       pushToActivityStack(activity);

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

        if (activityList.contains(activity)){
            activityList.remove(activity);
        }
    }

    public Activity topActivity(){
        if (!activityList.isEmpty()){
            WeakReference<Activity> top = activityList.getLast();
            if (top != null && top.get() != null){
                return top.get();
            }
        }
        return null;
    }

    public static void registSelf(Context context){
        Application application = (Application) context.getApplicationContext();
        application.registerActivityLifecycleCallbacks(sInstance);
        mContext = application.getApplicationContext();
    }




    public void pushToActivityStack(Activity activity) {
        activityList.add(new WeakReference<>(activity));
    }

    public void popFromActivityStack(Activity activity) {
        if (activityList == null){
            return;
        }
        for(int x=0; x<activityList.size(); x++){
            WeakReference<Activity> ref = activityList.get(x);
            if(ref!=null && ref.get()!=null && ref.get()==activity){
                activityList.remove(ref);
            }
        }
    }

    public void clearActivityStack(){
        try {
            if (activityList == null){
                return;
            }
            for (WeakReference<Activity> ref : activityList) {
                if (ref != null && ref.get() != null && !ref.get().isFinishing()) {
                    ref.get().finish();
                }
            }
        }catch (Throwable e){

        }finally {
            activityList.clear();
            activityList = null;
        }
    }


}
