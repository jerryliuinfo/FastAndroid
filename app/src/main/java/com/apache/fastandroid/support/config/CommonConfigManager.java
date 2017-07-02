package com.apache.fastandroid.support.config;

import com.tesla.framework.common.util.BaseSharedPreferenceConfigManager;

/**
 * Created by jerryliu on 2017/6/30.
 */

public class CommonConfigManager extends BaseSharedPreferenceConfigManager {
    public static final String SP_NAME = "sp_common";
    
    private static CommonConfigManager instance = null;
    private CommonConfigManager(){}
    public static CommonConfigManager getInstance() {
        if (instance == null) {
            synchronized (CommonConfigManager.class) {
                if (instance == null){
                    instance = new CommonConfigManager();
                }
            }
        }
        return instance;
    }
    
    @Override
    public String configSPFileName() {
        return SP_NAME;
    }
    
    public boolean isFirstSplash(){
        return getBooleanValue("isFirstSplash", true);
    }
    
    public void setFirstSplash(boolean value){
        setBooleanValue("isFirstSplash",value);
    }
}
