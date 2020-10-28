package com.apache.fastandroid.jetpack;

/**
 * Created by Jerry on 2020-06-09.
 */
public class GpsEngineLifeCycle  {
    private static volatile GpsEngineLifeCycle instance = null;
    private GpsEngineLifeCycle(){}
    public static GpsEngineLifeCycle getInstance() {
        if (instance == null) {
            synchronized (GpsEngineLifeCycle.class) {
                if (instance == null){
                    instance = new GpsEngineLifeCycle();
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



}
