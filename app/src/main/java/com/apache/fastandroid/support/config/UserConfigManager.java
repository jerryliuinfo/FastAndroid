package com.apache.fastandroid.support.config;

import com.alibaba.fastjson.JSON;
import com.apache.fastandroid.support.bean.UserBean;
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


    public void saveUserInfo(UserBean userBean){
        setStringValue("user_info", JSON.toJSONString(userBean));
    }
}
