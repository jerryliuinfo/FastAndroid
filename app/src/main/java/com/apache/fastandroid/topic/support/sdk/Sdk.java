package com.apache.fastandroid.topic.support.sdk;

import com.apache.fastandroid.R;
import com.apache.fastandroid.topic.news.NewsApiConstants;
import com.apache.fastandroid.topic.news.http.NewsHttpUtility;
import com.apache.fastandroid.topic.support.bean.NewsChannelTable;
import com.apache.fastandroid.topic.support.bean.NewsSummary;
import com.apache.fastandroid.topic.support.bean.NewsSummaryBeans;
import com.apache.fastandroid.topic.support.bean.WallpaperBeans;
import com.apache.fastandroid.topic.support.http.MyHttpUtility;
import com.blankj.utilcode.util.ResourceUtils;
import com.tesla.framework.common.setting.Setting;
import com.tesla.framework.network.biz.ABizLogic;
import com.tesla.framework.network.http.HttpConfig;
import com.tesla.framework.network.http.Params;
import com.tesla.framework.network.task.TaskException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 01370340 on 2017/11/19.
 */

public class Sdk extends ABizLogic {
    @Override
    protected HttpConfig configHttpConfig() {
        HttpConfig config = new HttpConfig();

        return config;
    }

    private Sdk(){

    }
    private Sdk(CacheMode cacheMode) {
        super(cacheMode);
    }

    public static Sdk newInstance(CacheMode mode){
        return new Sdk(mode);
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
        //action.getExtras().putString(CACHE_UTILITY, newSettingExtra(CACHE_UTILITY, WallpaperCacheUtility.class.getName(), ""));

        WallpaperBeans beans = doGet(getHttpConfig(),action, params, WallpaperBeans.class);
        if (beans.getItem() == null || beans.getItem().getWallpaperList() == null) {
            throw new TaskException(TaskException.TaskError.resultIllegal.toString());
        }
        return beans;
    }


    public List<NewsChannelTable> loadNewsChannelsStatic(){
        List<String> channelNameList = Arrays.asList(ResourceUtils.getStringArray(R.array.news_channel_name_static));
        List<String> channelIdList = Arrays.asList(ResourceUtils.getStringArray(R.array.news_channel_id_static));
        ArrayList<NewsChannelTable> newsChannelTables=new ArrayList<>();
        for (int i = 0; i < channelNameList.size(); i++) {
            NewsChannelTable entity = new NewsChannelTable(channelNameList.get(i), channelIdList.get(i)
                    , NewsApiConstants.getType(channelIdList.get(i)), i <= 5, i, true);
            newsChannelTables.add(entity);
        }
        return newsChannelTables;
    }


    /**
     *
     *  @GET("nc/article/{type}/{id}/{startPage}-20.html")
     *  baseUrl:http://c.m.163.com/
     * 获取新闻列表
     * @param type
     * @param id
     * @param startPage
     * @return
     */
    public NewsSummaryBeans getNewsListData(String type, final String id, String startPage)throws TaskException{
        //http://c.m.163.com/
        Setting action = newSetting("getNewsListData",assemblePath("nc/article",type,id,startPage) +("-20.html"),"获取最新壁纸列表");
        action.getExtras().put(BASE_URL, newSettingExtra(BASE_URL, "http://c.m.163.com/", ""));
        action.getExtras().put(HTTP_UTILITY, newSettingExtra(HTTP_UTILITY, NewsHttpUtility.class.getName(), ""));

        HttpConfig httpConfig = getHttpConfig();
        //httpConfig.addHeader("Cache-Control","max-age=0");

        Params params = new Params();
        params.addParameter("id",id);
        NewsSummary[] beans = doGet(httpConfig,action, params, NewsSummary[].class);

        List<NewsSummary> list = Arrays.asList(beans);
        return new NewsSummaryBeans(list);
    }


    public String assemblePath(String... params){
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < params.length; i++) {
            buffer.append(params[i]);
            if (i < params.length - 1){
                buffer.append("/");
            }
        }
        return buffer.toString();
    }



    public void submitScore(String result) throws TaskException{
        Setting action = newSetting("submitScore", "test", "提交分数");
        action.getExtras().put(HTTP_UTILITY, newSettingExtra(HTTP_UTILITY, MyHttpUtility.class.getName(), ""));

        // 配置缓存器
        HttpConfig config = new HttpConfig();
        config.baseUrl = "http://10.118.125.217:8081";


        doPost(getHttpConfig(),action,null,null,null,null);

    }



}

