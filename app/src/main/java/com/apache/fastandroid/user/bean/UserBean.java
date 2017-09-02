package com.apache.fastandroid.user.bean;

import com.apache.fastandroid.support.bean.BaseBean;
import com.apache.fastandroid.support.exception.ICheck;
import com.tesla.framework.network.task.TaskException;

/**
 * Created by jerryliu on 2017/7/9.
 */

public class UserBean extends BaseBean implements ICheck {
    private String userName;
    private String password;
    private String token;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public void check() throws TaskException {
        if ("invalid".equals(token)){
            throw new TaskException("101");
        }
    }
}
