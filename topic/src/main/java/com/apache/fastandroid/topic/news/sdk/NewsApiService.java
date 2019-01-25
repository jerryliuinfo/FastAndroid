package com.apache.fastandroid.topic.news.sdk;

import com.apache.fastandroid.topic.news.bean.NewsBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by 01370340 on 2017/9/3.
 */

public interface NewsApiService {
    /**
     * 获取 news 列表
     *
     * @param node_id 如果你需要只看某个节点的，请传此参数, 如果不传 则返回全部
     * @param offset  偏移数值，默认值 0
     * @param limit   数量极限，默认值 20，值范围 1..150
     * @return news 列表
     */
    @GET("news.json")
    Call<List<NewsBean>> getNewsList(@Query("node_id") Integer node_id, @Query("offset") Integer offset,
                                     @Query("limit") Integer limit);

}
