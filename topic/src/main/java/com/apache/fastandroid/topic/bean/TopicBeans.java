package com.apache.fastandroid.topic.bean;

import com.tesla.framework.network.biz.ResultBean;

import java.util.List;

/**
 * Created by 01370340 on 2017/9/2.
 */

public class TopicBeans extends ResultBean {
    public List<TopicBean> list;

    public TopicBeans(List<TopicBean> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "TopicBeans{" + "list=" + list + "} " + super.toString();
    }
}
