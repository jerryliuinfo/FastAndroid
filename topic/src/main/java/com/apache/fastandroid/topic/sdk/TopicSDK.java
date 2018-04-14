package com.apache.fastandroid.topic.sdk;


import android.text.TextUtils;

import com.apache.fastandroid.artemis.http.SingleRxHttp;
import com.apache.fastandroid.artemis.retrofit.BaseHttpUtilsV2;
import com.apache.fastandroid.artemis.retrofit.RetrofitClient;
import com.apache.fastandroid.artemis.rx.TransformerHelper;
import com.apache.fastandroid.artemis.rx.observer.CommonObserver;
import com.apache.fastandroid.topic.TopicConstans;
import com.apache.fastandroid.topic.bean.TopicBean;
import com.apache.fastandroid.topic.bean.TopicBeans;
import com.apache.fastandroid.topic.bean.TopicReplyBean;
import com.apache.fastandroid.topic.bean.TopicReplyBeans;
import com.apache.fastandroid.topic.cache.TopicCacheUtility;
import com.apache.fastandroid.topic.http.TopicListHttpUtility;
import com.tesla.framework.applike.FrameworkApplication;
import com.tesla.framework.common.setting.Setting;
import com.tesla.framework.network.biz.ABizLogic;
import com.tesla.framework.network.http.HttpConfig;
import com.tesla.framework.network.http.Params;
import com.tesla.framework.network.task.TaskException;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import retrofit2.Call;



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

    @Override
    protected HttpConfig configHttpConfig() {
        HttpConfig config = new HttpConfig();
        config.baseUrl = TopicConstans.BASE_URL;
        return config;
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
        Setting action = newSetting("topicsList","topics.json","主题列表");
        action.getExtras().put(CACHE_UTILITY,newSettingExtra(CACHE_UTILITY, TopicCacheUtility.class.getName(),"getTopicsList"));
        action.getExtras().put(HTTP_UTILITY,newSettingExtra(HTTP_UTILITY, TopicListHttpUtility.class.getName(),"getTopicsList"));
        Params params = new Params();
        params.addParameter("offset",String.valueOf(offset));
        params.addParameter("limit",String.valueOf(limit));

        return doGet(getHttpConfig(),action,params,TopicBeans.class);
    }




    public TopicBeans getTopicsListByObservable(String type, Integer node_id, int offset, int limit) throws TaskException {
        TopicApiService apiService = SingleRxHttp.newInstance().baseUrl(TopicConstans.BASE_URL).createSApi(TopicApiService.class);
        Observable<List<TopicBean>> observable =  apiService.getTopicsListByRetrofit(type,node_id,offset,limit);

        //异步变同步
        final CountDownLatch latch = new CountDownLatch(1);
        final TopicBeans[] beans = {null};

        final String[] errMsg = {""};
        observable.compose(TransformerHelper.<List<TopicBean>>switchSchedulers()).subscribe(new CommonObserver<List<TopicBean>>() {


            @Override
            protected void onFailed(String errorMsg) {
                errMsg[0] = errorMsg;
                latch.countDown();

            }

            @Override
            protected void onSuccess(List<TopicBean> t) {
                beans[0] = new TopicBeans();
                beans[0].setList(t);
                latch.countDown();
            }

            @Override
            public void doOnComplete() {
                super.doOnComplete();
                latch.countDown();
            }
        });
        try {
            latch.await(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (beans[0] == null){
            TaskException exception = new TaskException();
            if(TextUtils.isEmpty(errMsg[0])){
                errMsg[0] = "网络异常";
            }
            exception.setMessage(errMsg[0]);
            throw exception;
        }
        return beans[0];
    }

    /**
     * 获取主题详情
     * @param id
     * @param callback
     * @return
     */
   /* public Observable<TopicContent> getTopicsDetail(int id, final ICallback<TopicContent> callback) {
        *//*BaseHttpUtilsV2 httpUtils = BaseHttpUtilsV2.getInstance(FrameworkApplication.getContext(), TopicConstans.BASE_URL);
        TopicApiService apiService = httpUtils.getRetrofit().create(TopicApiService.class);
        Observable<TopicContent> observable =  apiService.getTopic(id);
        observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new DefaultHttpResultObserver<>(callback));
        return observable;*//*

        TopicApiService apiService = GlobalHttp.getInstance().getGlobalRetrofit().create(TopicApiService.class);
        Observable<TopicContent> observable =  apiService.getTopic(id);
        return observable;
    }*/

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
        List<TopicReplyBean> list = RetrofitClient.checkCallResult(call);
        if (list == null || list.size() == 0){
            throw new TaskException("没有回复数据");
        }
        return new TopicReplyBeans(list);
    }



}
