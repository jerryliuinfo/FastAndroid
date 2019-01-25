package com.apache.fastandroid.topic.news.http;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tesla.framework.common.setting.Setting;
import com.tesla.framework.network.http.HttpConfig;
import com.tesla.framework.network.http.OkHttpUtility;
import com.tesla.framework.network.http.Params;
import com.tesla.framework.network.task.TaskException;

/**
 * Created by 01370340 on 2017/11/23.
 */

public class NewsHttpUtility extends OkHttpUtility {


    String id = "";
    @Override
    public String getUrl(HttpConfig config, Setting action, Params urlParams) {
        //这里不要去拼接url了,
        String url =  config.baseUrl + action.getValue();
        id = urlParams.getParameter("id");
        return url;
    }

    @Override
    protected <T> T parseResponse(String resultStr, Class<T> responseCls) throws TaskException {
        if (!TextUtils.isEmpty(resultStr)){
            JSONObject jsonObject = JSON.parseObject(resultStr);
            if (jsonObject.containsKey(id)){
                T result = JSON.parseObject(jsonObject.getString(id), responseCls);
                return result;
            }
        }
        return super.parseResponse(resultStr, responseCls);
    }
}
