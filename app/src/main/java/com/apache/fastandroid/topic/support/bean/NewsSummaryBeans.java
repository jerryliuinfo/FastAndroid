package com.apache.fastandroid.topic.support.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 01370340 on 2017/11/23.
 */

public class NewsSummaryBeans implements Serializable {
   public List<NewsSummary> list;


    public NewsSummaryBeans(List<NewsSummary> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "NewsSummaryBeans{" + "list=" + list + '}';
    }
}

