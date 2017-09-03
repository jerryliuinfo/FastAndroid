package com.apache.fastandroid.news.sdk;

import com.apache.fastandroid.app.MyApplication;
import com.apache.fastandroid.artemis.BaseBizLogic;
import com.apache.fastandroid.news.bean.NewsBean;
import com.apache.fastandroid.news.bean.NewsBeans;
import com.apache.fastandroid.support.http.BaseHttpUtilsV2;
import com.apache.fastandroid.topic.TopicConstans;
import com.tesla.framework.network.http.HttpConfig;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by 01370340 on 2017/9/3.
 */

public class NewsSDK extends BaseBizLogic {
    @Override
    protected HttpConfig configHttpConfig() {
        return new HttpConfig();
    }
    
    public static NewsSDK newInstance(){
        return new NewsSDK();
    }

    public NewsBeans getNewsList(Integer node_id, int offset, int limit)throws Exception{
        BaseHttpUtilsV2 httpUtils = BaseHttpUtilsV2.getInstance(MyApplication.getContext(), TopicConstans.BASE_URL);
        NewsApiService apiService = httpUtils.getRetrofit().create(NewsApiService.class);
        Call<List<NewsBean>> call =  apiService.getNewsList(node_id,offset,limit);
        if (call != null){
            Response<List<NewsBean>> response = call.execute();
            if (response != null && response.isSuccessful()){
                List<NewsBean> list =  response.body();
                NewsBeans beans = new NewsBeans(list);
                return beans;
            }
        }
        return null;
    }
   
}
