package com.apache.fastandroid.bean;

import java.io.Serializable;

/**
 * Created by Jerry on 2021/6/10.
 */
public class VersionResponseBean implements Serializable {
    public int code;
    public String msg;
    public VersionInfo data;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"code\":").append(code);
        sb.append(",\"msg\":\"").append(msg).append('\"');
        sb.append(",\"data\":").append(data);
        sb.append('}');
        return sb.toString();
    }
}
