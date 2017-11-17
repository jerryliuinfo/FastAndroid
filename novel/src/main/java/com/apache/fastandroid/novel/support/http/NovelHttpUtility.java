package com.apache.fastandroid.novel.support.http;

import com.alibaba.fastjson.JSONObject;
import com.tesla.framework.network.http.OkHttpUtility;
import com.tesla.framework.network.task.TaskException;


/**
 * Created by 01370340 on 2017/11/4.
 */

public class NovelHttpUtility extends OkHttpUtility {

    @Override
    protected <T> T parseResponse(String resultStr, Class<T> responseCls) throws TaskException {

        JSONObject jsonObject = JSONObject.parseObject(resultStr);


        return super.parseResponse(resultStr, responseCls);
    }
}
