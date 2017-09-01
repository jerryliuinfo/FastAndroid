package com.apache.fastandroid.user.bean;

import com.apache.fastandroid.support.bean.BaseBean;

/**
 * Created by jerryliu on 2017/7/9.
 */

public class UserBean extends BaseBean {
    private String userName;
    private String password;

    public UserBean() {
    }

    public UserBean(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
