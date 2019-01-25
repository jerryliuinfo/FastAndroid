package com.apache.fastandroid.topic.support.config;

import android.content.Context;

import com.tesla.framework.common.util.sp.BaseSharedPreferenceConfigManager;

/**
 * Created by jerryliu on 2017/8/26.
 */

public class ADConfigManager extends BaseSharedPreferenceConfigManager {
    public ADConfigManager(Context context) {
        super(context);
    }

    @Override
    public String configSPFileName() {
        return "sp_ad";
    }

    private static ADConfigManager instance = null;
    public static ADConfigManager getInstance(Context context) {
        if (instance == null) {
            synchronized (ADConfigManager.class) {
                if (instance == null){
                    instance = new ADConfigManager(context);
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
