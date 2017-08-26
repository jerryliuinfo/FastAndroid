package com.apache.fastandroid.support.config;

import com.tesla.framework.common.util.BaseSharedPreferenceConfigManager;

/**
 * Created by jerryliu on 2017/8/26.
 */

public class ADConfigManager extends BaseSharedPreferenceConfigManager {
    @Override
    public String configSPFileName() {
        return "sp_ad";
    }

    private static ADConfigManager instance = null;
    private ADConfigManager(){}
    public static ADConfigManager getInstance() {
        if (instance == null) {
            synchronized (ADConfigManager.class) {
                if (instance == null){
                    instance = new ADConfigManager();
                }
            }
        }
        return instance;
    }

    public long getLastShowADTime(){
        return getLongValue("lastShowADTime",-1);
    }

    public void setLastShowADTime(long value){
        setLongValue("lastShowADTime",value);
    }

    public boolean isNeedToShowAd(){
        return System.currentTimeMillis() - getLastShowADTime() >= 5 * 60 * 1000;
    }
}
