package com.apache.fastandroid.user;

import android.text.TextUtils;

import com.apache.fastandroid.user.bean.UserBean;
import com.google.gson.Gson;
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




    private UserBean userBean;

    /**
     *
     * @return
     */
    public boolean isLastTimeLogined(){
        UserBean userBean = getUserBean();
        return userBean != null && !TextUtils.isEmpty(userBean.getUserName()) && !TextUtils.isEmpty(userBean.getPassword());
    }

    public void saveUserBean(UserBean userBean){
        if (userBean == null){
            return;
        }
        //对密码进行加密
       /* if (!TextUtils.isEmpty(userBean.getPassword())){
            userBean.setPassword(KeyGenerator.generateMD5(userBean.getPassword()));
        }*/
        setStringValue("user_info", new Gson().toJson(userBean));
    }

    public UserBean getUserBean(){
        if (userBean != null){
            return userBean;
        }
        String userInfoStr = getStringValue("user_info", "");
        if (!TextUtils.isEmpty(userInfoStr)){
            userBean =  new Gson().fromJson(userInfoStr,UserBean.class);
        }
        return userBean;
    }


}

