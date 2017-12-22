package com.apache.fastandroid.topic.http;

import com.alibaba.fastjson.JSON;
import com.apache.fastandroid.topic.bean.TopicBean;
import com.apache.fastandroid.topic.bean.TopicBeans;
import com.tesla.framework.common.util.ListUtil;
import com.tesla.framework.network.http.OkHttpUtility;
import com.tesla.framework.network.task.TaskException;

/**
 * Created by 01370340 on 2017/12/22.
 */

public class TopicListHttpUtility extends OkHttpUtility {
    @Override
    protected <T> T parseResponse(String resultStr, Class<T> responseCls) throws TaskException {
        if (responseCls.isAssignableFrom(String.class))
            return (T) resultStr;

        TopicBean[] array = JSON.parseObject(resultStr, TopicBean[].class);
        TopicBeans beans = new TopicBeans();
        beans.setList(ListUtil.arrayToList(array));
        return (T) beans;

    }
}
