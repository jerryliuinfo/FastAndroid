package com.apache.fastandroid.retrofit;


import androidx.annotation.Keep;

/**
 * description:
 * author hui.zhu
 * date 2017/3/6
 * copyright TCL-MIBC
 */
@Keep
public class Protocol<T> {
    public int code;
    public String msg;
    public T data;

    @Keep
    public static class Empty{}  // 不关注结果的


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
