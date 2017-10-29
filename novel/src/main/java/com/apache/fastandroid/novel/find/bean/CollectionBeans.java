package com.apache.fastandroid.novel.find.bean;

import com.tesla.framework.network.biz.ResultBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 01370340 on 2017/10/26.
 */

public class CollectionBeans extends ResultBean implements Serializable{

    public List<RecommendBook> list;

    public CollectionBeans() {
    }

    public CollectionBeans(List<RecommendBook> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "CollectionBeans{" + "list=" + list + '}';
    }


}
