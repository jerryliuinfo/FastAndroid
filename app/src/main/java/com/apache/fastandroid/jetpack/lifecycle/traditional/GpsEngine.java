package com.apache.fastandroid.jetpack.lifecycle.traditional;

import com.apache.fastandroid.jetpack.GpsCallback;
import com.tesla.framework.common.util.log.NLog;

/**
 * Created by Jerry on 2020-06-09.
 */
public class GpsEngine implements GpsCallback {
    public static final String TAG = GpsEngine.class.getSimpleName();
    private static volatile GpsEngine instance = null;
    private GpsEngine(){}
    public static GpsEngine getInstance() {
        if (instance == null) {
            synchronized (GpsEngine.class) {
                if (instance == null){
                    instance = new GpsEngine();
                }
            }
        }
        return instance;
    }

    private boolean isActive;

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }


    @Override
    public void onResumeAction() {
        NLog.d(TAG, "GpsEngine onResume");
        setActive(true);
    }

    @Override
    public void onPauseAction() {
        NLog.d(TAG, "GpsEngine onPause");
        setActive(false);
    }
}
