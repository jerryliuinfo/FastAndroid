package com.apache.fastandroid.topic.sdk;

import com.apache.fastandroid.topic.bean.TopicBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by 01370340 on 2017/9/2.
 */

public interface TopicApiService {

    /**
     * 获取 topic 列表
     *
     * @param type    类型，默认 last_actived，可选["last_actived", "recent", "no_reply", "popular", "excellent"]
     * @param node_id 如果你需要只看某个节点的，请传此参数, 如果不传 则返回全部
     * @param offset  偏移数值，默认值 0
     * @param limit   数量极限，默认值 20，值范围 1..150
     * @return topic 列表
     */
    @GET("topics.json")
    Call<List<TopicBean>> getTopicsList(@Query("type") String type, @Query("node_id") Integer node_id,
                                        @Query("offset") int offset, @Query("limit") int limit);
}
