package com.apache.fastandroid.pic;


import com.apache.fastandroid.artemis.retrofit.BaseHttpUtilsV2;
import com.apache.fastandroid.topic.support.bean.ImageResultBeans;
import com.apache.fastandroid.topic.support.sdk.APIService;
import com.apache.fastandroid.topic.support.sdk.ApiConstans;
import com.tesla.framework.applike.FrameworkApplication;
import com.tesla.framework.common.setting.Setting;
import com.tesla.framework.network.biz.ABizLogic;
import com.tesla.framework.network.http.HttpConfig;
import com.tesla.framework.network.http.Params;
import com.tesla.framework.network.task.TaskException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jerryliu on 2017/4/11.
 */

public class PicSDK extends ABizLogic {
    @Override
    protected HttpConfig configHttpConfig() {
        HttpConfig httpConfig = new HttpConfig();
        httpConfig.baseUrl = ApiConstans.Urls.BAIDU_IMAGES_URLS;
        return httpConfig;
    }

    public PicSDK() {
    }

    private PicSDK(CacheMode cacheMode) {
        super(cacheMode);
    }

    public static PicSDK newInstance(){
        return new PicSDK();
    }

    public static PicSDK newInstance(CacheMode mode){
        return new PicSDK(mode);
    }


    public ImageResultBeans loadImageData(String category, int pageNum) throws TaskException{
        Setting setting = newSetting("loadImages","data/imgs","加载图片");
        //setting.getExtras().putString(CACHE_UTILITY, newSettingExtra("loadImages",PicCacheUtility.class.getName(),"加载图片缓存"));

        Params params = new Params();
        params.addParameter("col",category);
        params.addParameter("tag","全部");
        params.addParameter("pn",String.valueOf(pageNum));
        params.addParameter("rn","10");
        params.addParameter("from", "1");


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fanyi.youdao.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return doGet(configHttpConfig(),setting,params,ImageResultBeans.class);
    }
    public Call<ImageResultBeans> loadImageDataV2(String category, int pageNum) throws TaskException{
        BaseHttpUtilsV2 httpUtils = BaseHttpUtilsV2.getInstance(FrameworkApplication.getContext(),ApiConstans.Urls.BAIDU_IMAGES_URLS);
        //httpUtils.setLogLevel(true, HttpLoggingInterceptor.Level.BODY);
        APIService apiService = httpUtils.getRetrofit().create(APIService.class);
        return apiService.loadImages(category,"全部",String.valueOf(pageNum),"10","1");
    }



}
