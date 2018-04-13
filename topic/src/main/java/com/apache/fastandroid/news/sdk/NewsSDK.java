package com.apache.fastandroid.news.sdk;

import com.apache.fastandroid.artemis.http.SingleRxHttp;
import com.apache.fastandroid.artemis.retrofit.RetrofitClient;
import com.apache.fastandroid.news.bean.NewsBean;
import com.apache.fastandroid.news.bean.NewsBeans;
import com.apache.fastandroid.topic.TopicConstans;
import com.tesla.framework.network.biz.ABizLogic;
import com.tesla.framework.network.http.HttpConfig;
import com.tesla.framework.network.task.TaskException;

import java.util.List;

import retrofit2.Call;

/**
 * Created by 01370340 on 2017/9/3.
 */

public class NewsSDK extends ABizLogic {
    @Override
    protected HttpConfig configHttpConfig() {
        return new HttpConfig();
    }
    
    public static NewsSDK newInstance(){
        return new NewsSDK();
    }


    public NewsBeans getNewsList(Integer node_id, int offset, int limit)throws TaskException{
        NewsApiService apiService = SingleRxHttp.newInstance().baseUrl(TopicConstans.BASE_URL).createSApi(NewsApiService.class);
        Call<List<NewsBean>> call =  apiService.getNewsList(node_id,offset,limit);
        List<NewsBean> list = RetrofitClient.checkCallResult(call);
        return new NewsBeans(list);
    }



}
