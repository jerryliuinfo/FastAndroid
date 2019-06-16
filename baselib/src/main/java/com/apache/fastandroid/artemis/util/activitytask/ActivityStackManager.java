package com.apache.fastandroid.artemis.util.activitytask;

import android.app.Activity;
import android.os.Bundle;
import java.lang.ref.WeakReference;
import java.util.LinkedList;

/**
 * Created by 01370340 on 2018/4/4.
 * 可以对activity生命周期方法内做一些业务处理，例如埋点
 */

public class ActivityStackManager extends ActivityLifeCallback {
    private static ActivityStackManager sInstance = new ActivityStackManager();

    private LinkedList<WeakReference<Activity>> activityList = new LinkedList<>();

    public static ActivityStackManager get(){
        return sInstance;
    }


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        super.onActivityCreated(activity, savedInstanceState);
        pushToActivityStack(activity);

    }


    @Override
    public void onActivityDestroyed(Activity activity) {
        super.onActivityDestroyed(activity);
        if (activityList.contains(activity)){
            activityList.remove(activity);
        }
    }

    public Activity getTopActivity(){
        if (!activityList.isEmpty()){
            WeakReference<Activity> top = activityList.getLast();
            if (top != null && top.get() != null){
                return top.get();
            }
        }
        return null;
    }



    /**
     * 添加到activy栈中
     * @param activity
     */
    public void pushToActivityStack(Activity activity) {
        activityList.add(new WeakReference<>(activity));
    }

    public void popFromActivityStack(Activity activity) {
        if (activityList == null){
            return;
        }
        for(int x = 0; x < activityList.size(); x++){
            WeakReference<Activity> ref = activityList.get(x);
            if(ref != null && ref.get() != null && ref.get() == activity){
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
