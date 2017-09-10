package com.apache.fastandroid.support.sdk;

import com.apache.fastandroid.app.MyApplication;
import com.apache.fastandroid.artemis.BaseBizLogic;
import com.apache.fastandroid.artemis.retrofit.BaseHttpUtils;
import com.apache.fastandroid.artemis.support.bean.BaseBean;
import com.apache.fastandroid.support.config.PublishVersionManager;
import com.apache.fastandroid.update.UpdateBean;
import com.tesla.framework.network.http.HttpConfig;
import com.tesla.framework.network.task.TaskException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by 01370340 on 2017/9/2.
 * 一些公用的接口请求，例如版本更新
 */

public class CommonSDK extends BaseBizLogic {
    @Override
    protected HttpConfig configHttpConfig() {
        return new HttpConfig();
    }

    public UpdateBean checkAppVersion() throws Exception{
        BaseHttpUtils httpUtils = BaseHttpUtils.getInstance(MyApplication.getContext(),ApiConstans.Urls.BAIDU_IMAGES_URLS);
        APIService apiService = httpUtils.getRetrofit().create(APIService.class);
        Call<BaseBean<UpdateBean>> call =  apiService.checkAppVersion(PublishVersionManager.getVersionCode());
        if (call != null){
            Response<BaseBean<UpdateBean>> response = call.execute();
            if (response != null && response.body() != null){
                BaseBean<UpdateBean> responseBean = response.body();
                checkRepsonse(responseBean);
                return responseBean.getData();
            }
        }
        throw new TaskException("server error");
    }
}
