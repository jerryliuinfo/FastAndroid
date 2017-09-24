package com.apache.fastandroid;

import android.content.Context;

/**
 * Created by 01370340 on 2017/9/24.
 */

public class ConfigManager {

    private static Context sContext;


    private static volatile ConfigManager instance = null;
    private ConfigManager(){}
    public static ConfigManager getInstance(Context context) {
        if (instance == null) {
            synchronized (ConfigManager.class) {
                if (instance == null){
                    sContext = context;
                    instance = new ConfigManager();
                }
            }
        }
        return instance;
    }
}
