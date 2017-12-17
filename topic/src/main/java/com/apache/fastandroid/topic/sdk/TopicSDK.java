package com.apache.fastandroid.topic.sdk;


import com.apache.fastandroid.artemis.BaseBizLogic;
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
import com.tesla.framework.FrameworkApplication;
import com.tesla.framework.common.setting.Setting;
import com.tesla.framework.common.setting.SettingUtility;
import com.tesla.framework.common.util.ListUtil;
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

public class TopicSDK extends BaseBizLogic {

    public static TopicSDK newInstance(){
        return new TopicSDK();
    }


    /**
     * https://diycode.cc/api/v3/topics.json?offset=0&limit=20
     * @param type
     * @param node_id
     * @param offset
     * @param limit
     * @return okhttp,HttpUrlConnection,Volley
     * @throws TaskException
     */
    public TopicBeans getTopicsList(String type, Integer node_id, int offset, int limit) throws TaskException {
        //1.判断有没有缓存

        Setting action = SettingUtility.getSetting("getTopicsList");
        Params params = new Params();
        params.addParameter("offset",String.valueOf(offset));
        params.addParameter("limit",String.valueOf(limit));
        TopicBean[] topicBeanArray = doGet(getHttpConfig(),action,params,TopicBean[].class);

        TopicBeans topicBeans = new TopicBeans(ListUtil.arrayToList(topicBeanArray));
        return topicBeans;
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
