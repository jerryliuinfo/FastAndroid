

package com.tesla.framework.common.util.activitytask;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

import com.tesla.framework.common.util.log.NLog;

public class ActivityTaskMgr {
    public static final String TAG = ActivityTaskMgr.class.getSimpleName();

    private List<WeakReference<Activity>> activityList = new ArrayList<>();

    private static ActivityTaskMgr       sInstance = null;

    private ActivityTaskMgr(){
    }

    /**
     * Get the instance of this.
     * 
     * @return instance
     */
    public static synchronized ActivityTaskMgr getInstance() {
        if (sInstance == null) {
            sInstance = new ActivityTaskMgr();
        }
        return sInstance;
    }

    public List<WeakReference<Activity>> getActivityList(){
        return activityList;
    }

    public Activity peekTopActivity(){
        if(activityList!=null && activityList.size()>0){
            WeakReference<Activity> ref = activityList.get(activityList.size()-1);
            if(ref!=null && ref.get()!=null){
                return ref.get();
            }
        }
        return null;
    }

    public void pushToActivityStack(Activity activity) {
        NLog.d(TAG, "pushToActivityStack activity: %s",activity);

        if (activityList == null){
            activityList = new ArrayList<>();
        }
        activityList.add(new WeakReference<>(activity));
    }

    public void popFromActivityStack(Activity activity) {
        NLog.d(TAG, "popFromActivityStack activity: %s",activity);
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

    public boolean isActivityStackEmpty() {
        return sizeOfActivityStack() == 0;
    }
    
	public int sizeOfActivityStack() {
		return activityList != null ? activityList.size() : 0;
	}


}
