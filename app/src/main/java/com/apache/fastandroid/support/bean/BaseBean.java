package com.apache.fastandroid.support.bean;

import java.io.Serializable;

/**
 * Created by 01370340 on 2017/9/1.
 */

public class BaseBean<T> implements Serializable {
    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
