package com.apache.fastandroid.site.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 01370340 on 2017/9/12.
 */

public class SiteBeans implements Serializable{
    public List<SitesBean> list;

    public SiteBeans(List<SitesBean> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "SiteBeans{" + "list=" + list + '}';
    }
}
