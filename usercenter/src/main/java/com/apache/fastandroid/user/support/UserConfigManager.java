package com.apache.fastandroid.user.support;

import com.tesla.framework.common.util.BaseSharedPreferenceConfigManager;

/**
 * Created by jerryliu on 2017/7/9.
 */

public class UserConfigManager extends BaseSharedPreferenceConfigManager{
    public static final String SP_NAME = "user";
    @Override
    public String configSPFileName() {
        return SP_NAME;
    }

    private static UserConfigManager instance = null;
    private UserConfigManager(){}
    public static UserConfigManager getInstance() {
        if (instance == null) {
            synchronized (UserConfigManager.class) {
                if (instance == null){
                    instance = new UserConfigManager();
                }
            }
        }
        return instance;
    }


    public void savePwd(String pwd){
        setStringValue("password", pwd);
    }

    public String getPwd(){
        return getStringValue("password", "");
    }


}

