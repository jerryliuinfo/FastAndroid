package com.apache.fastandroid.support.config;

import android.text.TextUtils;

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

    public void setUserName(String userName){
        setStringValue("user_name", userName);
    }

    public void setPwd(String pwd){
        setStringValue("user_pwd", pwd);
    }

    public String getUserName(){
        return getStringValue("user_name","");
    }

    public String getPwd(){
        return getStringValue("user_pwd","");
    }


    public boolean autoLogin(){
        return !TextUtils.isEmpty(UserConfigManager.getInstance().getUserName())
                && !TextUtils.isEmpty(UserConfigManager.getInstance().getPwd());
    }


}

