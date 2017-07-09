package com.apache.fastandroid.support.bean;

import java.io.Serializable;

/**
 * Created by jerryliu on 2017/7/9.
 */

public class UserBean implements Serializable {
    public String userName;
    public String password;


    public UserBean(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
