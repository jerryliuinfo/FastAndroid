package com.apache.fastandroid.novel.find.bean;

import com.apache.fastandroid.novel.bean.Base;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 01370340 on 2017/10/18.
 */

public class RecommandBeans extends Base implements Serializable {

    public List<RecommendBean> booklists;


    public static class RecommendBean implements Serializable {
        public String id;
        public String title;
        public String author;
        public String desc;
        public int bookCount;
        public String cover;
        public int collectorCount;
    }


}
