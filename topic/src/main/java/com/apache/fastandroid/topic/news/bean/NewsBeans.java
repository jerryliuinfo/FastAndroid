package com.apache.fastandroid.topic.news.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 01370340 on 2017/9/3.
 */

public class NewsBeans implements Serializable{
    public List<NewsBean> list;

    public NewsBeans(List<NewsBean> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "NewsBeans{" + "list=" + list + '}';
    }
}
