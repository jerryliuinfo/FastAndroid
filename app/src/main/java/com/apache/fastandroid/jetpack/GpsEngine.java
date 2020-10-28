package com.apache.fastandroid.jetpack;

/**
 * Created by Jerry on 2020-06-09.
 */
public class GpsEngine implements GpsCallback{
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
        setActive(true);
    }

    @Override
    public void onPauseAction() {
        setActive(false);
    }
}
