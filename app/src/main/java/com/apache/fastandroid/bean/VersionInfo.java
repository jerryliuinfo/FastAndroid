package com.apache.fastandroid.bean;


import java.io.Serializable;

import androidx.annotation.Keep;


@Keep
public class VersionInfo implements Serializable {
    public static final int UPDATE_FORCE = 1;

    public String versionName;    //apk最新版本name
    public int versionCode;    //apk最新版本号
    public float size;          //文件大小,如2048byte
    public String packageUrl;    //apk下载更新地址
    public String description;    //版本升级说明
    public String type;        //是否需要强制更新（normal、silent）
    public int progress;
    public String path;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"versionName\":\"").append(versionName).append('\"');
        sb.append(",\"versionCode\":").append(versionCode);
        sb.append(",\"size\":").append(size);
        sb.append(",\"packageUrl\":\"").append(packageUrl).append('\"');
        sb.append(",\"description\":\"").append(description).append('\"');
        sb.append(",\"type\":\"").append(type).append('\"');
        sb.append(",\"progress\":").append(progress);
        sb.append(",\"path\":\"").append(path).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
