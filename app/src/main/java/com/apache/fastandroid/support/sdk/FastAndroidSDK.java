package com.apache.fastandroid.support.sdk;

import com.apache.fastandroid.support.bean.ImageResultBeans;
import com.tesla.framework.common.setting.Setting;
import com.tesla.framework.network.biz.ABizLogic;
import com.tesla.framework.network.http.HttpConfig;
import com.tesla.framework.network.http.Params;
import com.tesla.framework.network.task.TaskException;

/**
 * Created by jerryliu on 2017/4/11.
 */

public class FastAndroidSDK extends ABizLogic {
    @Override
    protected HttpConfig configHttpConfig() {
        HttpConfig httpConfig = new HttpConfig();
        httpConfig.baseUrl = ApiConstans.Urls.BAIDU_IMAGES_URLS;
        return httpConfig;
    }

    public FastAndroidSDK() {
    }

    public FastAndroidSDK(CacheMode cacheMode) {
        super(cacheMode);
    }

    public static FastAndroidSDK newInstance(){
        return new FastAndroidSDK();
    }

    public static FastAndroidSDK newInstance(CacheMode mode){
        return new FastAndroidSDK(mode);
    }


    public ImageResultBeans loadImageData(String category, int pageNum) throws TaskException{
        Setting setting = newSetting("loadImages","data/imgs","加载图片");

        /*StringBuffer sb = new StringBuffer();
        sb.append(ApiConstants.Urls.BAIDU_IMAGES_URLS);
        sb.append("?col=");
        try {
            sb.append(URLEncoder.encode(category, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        sb.append("&tag=");
        try {
            sb.append(URLEncoder.encode("全部", "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        sb.append("&pn=");
        sb.append(pageNum * PAGE_LIMIT);
        sb.append("&rn=");
        sb.append(PAGE_LIMIT);
        sb.append("&from=1");*/


        Params params = new Params();
        params.addParameter("col",category);
        params.addParameter("tag","全部");
        params.addParameter("pn",String.valueOf(pageNum));
        params.addParameter("rn","10");
        params.addParameter("from", "1");

        return doGet(configHttpConfig(),setting,params,ImageResultBeans.class);
    }



}
