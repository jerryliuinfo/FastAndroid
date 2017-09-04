package com.apache.fastandroid.user.support;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.apache.fastandroid.artemis.support.bean.Token;
import com.apache.fastandroid.artemis.support.bean.UserBean;
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
            setStringValue("user_info", "");
            return;
        }
        //对密码进行加密
       /* if (!TextUtils.isEmpty(userBean.getPassword())){
            userBean.setPassword(KeyGenerator.generateMD5(userBean.getPassword()));
        }*/
        setStringValue("user_info", new Gson().toJson(userBean));
    }

    public UserBean getUserBean(){
        String userInfoStr = getStringValue("user_info", "");
        if (!TextUtils.isEmpty(userInfoStr)){
            return  new Gson().fromJson(userInfoStr,UserBean.class);
        }
        return null;
    }


    public void saveToken(@NonNull Token token){
        setStringValue("token", new Gson().toJson(token));
    }

    public Token getToken(){
        String token = getStringValue("token", null);
        if (!TextUtils.isEmpty(token)){
            return new Gson().fromJson(token,Token.class);
        }
        return null;
    }

    public void clearToken(){
        setStringValue("token", "");
    }


}

