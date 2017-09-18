package com.apache.fastandroid.site.sdk;

import com.apache.fastandroid.artemis.retrofit.BaseHttpUtilsV2;
import com.apache.fastandroid.site.bean.SiteBeans;
import com.apache.fastandroid.site.bean.SitesBean;
import com.apache.fastandroid.topic.TopicConstans;
import com.tesla.framework.FrameworkApplication;
import com.tesla.framework.network.biz.ABizLogic;
import com.tesla.framework.network.http.HttpConfig;
import com.tesla.framework.network.task.TaskException;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by 01370340 on 2017/9/12.
 */

public class SiteSDK extends ABizLogic {
    @Override
    protected HttpConfig configHttpConfig() {
        return new HttpConfig();
    }

    public static SiteSDK newInstance(){
        return new SiteSDK();
    }

    public SiteBeans getSiteList() throws TaskException {
        BaseHttpUtilsV2 httpUtils = BaseHttpUtilsV2.getInstance(FrameworkApplication.getContext(), TopicConstans.BASE_URL);
        SitesApiService apiService = httpUtils.getRetrofit().create(SitesApiService.class);
        Call<List<SitesBean>> call =  apiService.getSites();
        if (call != null){
            Response<List<SitesBean>> response = null;
            try {
                response = call.execute();
                if (response != null && response.isSuccessful()){
                    List<SitesBean> list =  response.body();
                    SiteBeans beans = new SiteBeans(list);
                    return beans;
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new TaskException(e.getMessage());
            }

        }
        return null;
    }
}
