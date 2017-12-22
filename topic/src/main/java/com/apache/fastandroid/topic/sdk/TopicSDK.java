package com.apache.fastandroid.topic.sdk;


import com.apache.fastandroid.artemis.retrofit.BaseHttpUtilsV2;
import com.apache.fastandroid.artemis.retrofit.RetrofitUtil;
import com.apache.fastandroid.artemis.rx.DefaultHttpResultObserver;
import com.apache.fastandroid.artemis.rx.ICallback;
import com.apache.fastandroid.topic.TopicConstans;
import com.apache.fastandroid.topic.bean.TopicBean;
import com.apache.fastandroid.topic.bean.TopicBeans;
import com.apache.fastandroid.topic.bean.TopicContent;
import com.apache.fastandroid.topic.bean.TopicReplyBean;
import com.apache.fastandroid.topic.bean.TopicReplyBeans;
import com.apache.fastandroid.topic.cache.TopicCacheUtility;
import com.apache.fastandroid.topic.http.TopicListHttpUtility;
import com.tesla.framework.FrameworkApplication;
import com.tesla.framework.common.setting.Setting;
import com.tesla.framework.common.setting.SettingUtility;
import com.tesla.framework.network.biz.ABizLogic;
import com.tesla.framework.network.http.HttpConfig;
import com.tesla.framework.network.http.Params;
import com.tesla.framework.network.task.TaskException;

import java.util.List;

import retrofit2.Call;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by 01370340 on 2017/9/2.
 */

public class TopicSDK extends ABizLogic {

    private TopicSDK(){

    }
    private TopicSDK(CacheMode mode){
        super(mode);
    }

    public static TopicSDK newInstance(){
        return new TopicSDK();
    }

    public static TopicSDK newInstance(CacheMode mode){
        return new TopicSDK(mode);
    }

    /**
     * https://diycode.cc/api/v3/topics.json?offset=0&limit=20
     * @param type
     * @param node_id
     * @param offset
     * @param limit
     * @return okhttp,HttpUrlConnection,Volley
     *
     *
     *
     * [
        {
        "id": 1053,
        "title": "2018年38 种自由职业大盘点",
        "created_at": "2017-12-22T15:31:22.849+08:00",
        "updated_at": "2017-12-22T15:31:22.849+08:00",
        "replied_at": null,
        "replies_count": 0,
        "node_name": "酷工作",
        "node_id": 25,
        "last_reply_user_id": null,
        "last_reply_user_login": null,
        "user": {
            "id": 6259,
            "login": "simoncc",
            "name": "Simon",
            "avatar_url": "https://diycode.cc/system/letter_avatars/2/S/162_136_126/240.png"
        },
        "deleted": false,
        "excellent": false,
        "abilities": {
            "update": false,
            "destroy": false
            }
        },
        {
            "id": 1050,
            "title": "OpeningStartAnimation - Android 开屏动画 view",
            "created_at": "2017-12-20T10:03:58.570+08:00",
            "updated_at": "2017-12-20T10:03:58.570+08:00",
            "replied_at": null,
            "replies_count": 0,
            "node_name": "Android",
            "node_id": 1,
            "last_reply_user_id": null,
            "last_reply_user_login": null,
            "user": {
                "id": 6064,
                "login": "joshua",
                "name": null,
                "avatar_url": "https://diycode.cc/system/letter_avatars/2/J/82_188_137/240.png"
            },
            "deleted": false,
            "excellent": false,
            "abilities": {
            "update": false,
            "destroy": false
            }
        }
    ]
     * @throws TaskException
     */
    public TopicBeans getTopicsList(String type, Integer node_id, int offset, int limit) throws TaskException {
        //1.判断有没有缓存

        Setting action = SettingUtility.getSetting("getTopicsList");
        action.getExtras().put(CACHE_UTILITY,newSettingExtra(CACHE_UTILITY, TopicCacheUtility.class.getName(),"getTopicsList"));
        action.getExtras().put(HTTP_UTILITY,newSettingExtra(HTTP_UTILITY, TopicListHttpUtility.class.getName(),"getTopicsList"));
        Params params = new Params();
        params.addParameter("offset",String.valueOf(offset));
        params.addParameter("limit",String.valueOf(limit));

        return doGet(getHttpConfig(),action,params,TopicBeans.class);
    }




    public Observable<List<TopicBean>> getTopicsListByObservable(String type, Integer node_id, int offset, int limit) throws Exception{
        BaseHttpUtilsV2 httpUtils = BaseHttpUtilsV2.getInstance(FrameworkApplication.getContext(), TopicConstans.BASE_URL);
        TopicApiService apiService = httpUtils.getRetrofit().create(TopicApiService.class);
        Observable<List<TopicBean>> observable =  apiService.getTopicsListV2(type,node_id,offset,limit);
        return observable;
    }

    /**
     * 获取主题详情
     * @param id
     * @param callback
     * @return
     */
    public Observable<TopicContent> getTopicsDetail(int id, final ICallback<TopicContent> callback) {
        BaseHttpUtilsV2 httpUtils = BaseHttpUtilsV2.getInstance(FrameworkApplication.getContext(), TopicConstans.BASE_URL);
        TopicApiService apiService = httpUtils.getRetrofit().create(TopicApiService.class);
        Observable<TopicContent> observable =  apiService.getTopic(id);
        observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new DefaultHttpResultObserver<>(callback));
        return observable;
    }

    /**
     * 获取评论列表
     * @param id
     * @param offset
     * @param limit
     * @return
     * @throws TaskException
     */
    public TopicReplyBeans getReplyList(int id, Integer offset, Integer limit)throws TaskException {
        BaseHttpUtilsV2 httpUtils = BaseHttpUtilsV2.getInstance(FrameworkApplication.getContext(), TopicConstans.BASE_URL);
        TopicApiService apiService = httpUtils.getRetrofit().create(TopicApiService.class);
        Call<List<TopicReplyBean>> call =  apiService.getTopicRepliesList(id,offset,limit);
        List<TopicReplyBean> list = RetrofitUtil.checkCallResult(call);
        if (list == null || list.size() == 0){
            throw new TaskException("没有回复数据");
        }
        return new TopicReplyBeans(list);
    }


    @Override
    protected HttpConfig configHttpConfig() {
        HttpConfig config = new HttpConfig();
        config.baseUrl = TopicConstans.BASE_URL;
        return config;
    }
}
