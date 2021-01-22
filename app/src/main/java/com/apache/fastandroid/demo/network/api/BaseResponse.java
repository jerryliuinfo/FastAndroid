package com.apache.fastandroid.demo.network.api;

import java.io.Serializable;

import androidx.annotation.Keep;

/**
 * Created by Jerry on 2020/11/25.
 */
@Keep
public class BaseResponse implements Serializable {
    public int code;
    public String msg;

    /**
     * 判断数据是否是有效的
     * @return
     */
    public boolean isSuccess() {
        return code == 0 && "Success".equalsIgnoreCase(msg);
    }
}
