package com.apache.fastandroid.user.support;

import android.content.Context;

import com.tesla.framework.common.util.sp.BaseSharedPreferenceConfigManager;

/**
 * Created by jerryliu on 2017/7/9.
 */

public class UserConfigManager extends BaseSharedPreferenceConfigManager {
    public static final String SP_NAME = "user";


    public UserConfigManager(Context context) {
        super(context);
    }

    @Override
    public String configSPFileName() {
        return SP_NAME;
    }

    private static UserConfigManager instance = null;
    //private UserConfigManager(){}
    public static UserConfigManager getInstance(Context context) {
        if (instance == null) {
            synchronized (UserConfigManager.class) {
                if (instance == null){
                    instance = new UserConfigManager(context);
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

