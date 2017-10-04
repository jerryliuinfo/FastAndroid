package com.apache.fastandroid.topic.sdk;


import com.apache.fastandroid.artemis.BaseBizLogic;
import com.apache.fastandroid.artemis.retrofit.BaseHttpUtilsV2;
import com.apache.fastandroid.artemis.rx.DefaultHttpResultObserver;
import com.apache.fastandroid.artemis.rx.ICallback;
import com.apache.fastandroid.topic.TopicConstans;
import com.apache.fastandroid.topic.bean.TopicBean;
import com.apache.fastandroid.topic.bean.TopicBeans;
import com.apache.fastandroid.topic.bean.TopicContent;
import com.apache.fastandroid.topic.bean.TopicReplyBean;
import com.tesla.framework.FrameworkApplication;
import com.tesla.framework.network.http.HttpConfig;
import com.tesla.framework.network.task.TaskException;

import java.util.ArrayList;
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
     * 获取帖子列表
     * @param type
     * @param node_id
     * @param offset
     * @param limit
     * @return
     * @throws TaskException
     */
    public TopicBeans getTopicsList(String type, Integer node_id, int offset, int limit) throws TaskException {
        //1.判断有没有缓存
        BaseHttpUtilsV2 httpUtils = BaseHttpUtilsV2.getInstance(FrameworkApplication.getContext(), TopicConstans.BASE_URL);
        TopicApiService apiService = httpUtils.getRetrofit().create(TopicApiService.class);
        Call<List<TopicBean>> call =  apiService.getTopicsList(type,node_id,offset,limit);
        List<TopicBean> list =  checkCallResult(call);

        return new TopicBeans(list);
    }

    public Observable<List<TopicBean>> getTopicsListV2(String type, Integer node_id, int offset, int limit) throws Exception{
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
    public ArrayList<TopicReplyBean> getReplyList(int id, Integer offset, Integer limit)throws TaskException {
        BaseHttpUtilsV2 httpUtils = BaseHttpUtilsV2.getInstance(FrameworkApplication.getContext(), TopicConstans.BASE_URL);
        TopicApiService apiService = httpUtils.getRetrofit().create(TopicApiService.class);
        Call<List<TopicReplyBean>> call =  apiService.getTopicRepliesList(id,offset,limit);
        List<TopicReplyBean> list = checkCallResult(call);
        if (list != null){
            //缓存数据
        }
        return new ArrayList(list);
    }


    @Override
    protected HttpConfig configHttpConfig() {
        return null;
    }
}
