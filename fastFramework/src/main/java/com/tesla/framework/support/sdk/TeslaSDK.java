package com.tesla.framework.support.sdk;

import com.tesla.framework.common.setting.Setting;
import com.tesla.framework.network.biz.ABizLogic;
import com.tesla.framework.network.http.HttpConfig;
import com.tesla.framework.network.http.Params;
import com.tesla.framework.network.task.TaskException;
import com.tesla.framework.support.bean.HomeBeans;

/**
 * Created by jerryliu on 2017/4/4.
 */

public class TeslaSDK extends ABizLogic {
    public static TeslaSDK newInstance(){
        return new TeslaSDK();
    }

    public TeslaSDK() {
    }

    public TeslaSDK(CacheMode cacheMode) {
        super(cacheMode);
    }

    public static TeslaSDK newInstance(CacheMode cacheMode){
        return new TeslaSDK(cacheMode);
    }



    public HomeBeans loadPerson(int index, int pageSize) throws TaskException{

        Params params = new Params();
        params.addParameter("index", String.valueOf(index));
        params.addParameter("pageSize", String.valueOf(pageSize));
        Setting setting = newSetting("loadPerson","loadPerson", "加载主页");

        //setting.getExtras().putString(HTTP_UTILITY, newSettingExtra(HTTP_UTILITY,HomeHttpUtility.class.getName(),""));
        //setting.getExtras().putString(CACHE_UTILITY,newSettingExtra(CACHE_UTILITY,HomeCacheUtility.class.getName(),"" ));
        return doGet(configHttpConfig(), setting, params, HomeBeans.class);
    }

    @Override
    protected HttpConfig configHttpConfig() {
        HttpConfig config = new HttpConfig();
        config.baseUrl = "www.baidu.com";
        return config;
    }
}
