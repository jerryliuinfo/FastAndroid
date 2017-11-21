package com.apache.fastandroid.support.sdk;

import com.apache.fastandroid.support.bean.WallpaperBeans;
import com.tesla.framework.common.setting.Setting;
import com.tesla.framework.network.biz.ABizLogic;
import com.tesla.framework.network.http.HttpConfig;
import com.tesla.framework.network.http.Params;
import com.tesla.framework.network.task.TaskException;

/**
 * Created by 01370340 on 2017/11/19.
 */

public class Sdk extends ABizLogic {
    @Override
    protected HttpConfig configHttpConfig() {
        HttpConfig config = new HttpConfig();

        return config;
    }

    public static Sdk newInstance(){
        return new Sdk();
    }




    public WallpaperBeans getWallpaperList(int page) throws TaskException{
        Setting action = newSetting("getWallpaper", "wallpaper/newestorhot/content", "获取最新壁纸列表");
        action.getExtras().put(BASE_URL, newSettingExtra(BASE_URL, "http://apps.tclclouds.com/api/", ""));

        Params params = new Params();
        params.addParameter("flag", "2");// 1：最新；2：最热）
        params.addParameter("page", String.valueOf(page));
        params.addParameter("per_page", "30");
        params.addParameter("encoder", "debug");

        // 配置缓存器
        //action.getExtras().put(CACHE_UTILITY, newSettingExtra(CACHE_UTILITY, WallpaperCacheUtility.class.getName(), ""));

        WallpaperBeans beans = doGet(getHttpConfig(),action, params, WallpaperBeans.class);
        if (beans.getItem() == null || beans.getItem().getWallpaperList() == null) {
            throw new TaskException(TaskException.TaskError.resultIllegal.toString());
        }
        return beans;
    }
}
