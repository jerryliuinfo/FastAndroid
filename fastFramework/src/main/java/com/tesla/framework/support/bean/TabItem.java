package com.tesla.framework.support.bean;

import java.io.Serializable;

/**
 * ViewPager的Tab页标签
 *
 */
public class TabItem implements Serializable,ITabItem{

    private static final long serialVersionUID = -1162756298239591517L;

    private String type;

    private String title;

    private Serializable tag;

    //必须要有title属性
    public TabItem(String type, String title) {
        this.type = type;
        this.title = title;
    }

    /**
     * 必须要有title属性
     * @param title
     * @param tag
     */
    public TabItem(String title,Serializable tag) {
        this.title = title;
        this.tag = tag;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Serializable getTag() {
        return tag;
    }

    public void setTag(Serializable tag) {
        this.tag = tag;
    }

    @Override
    public String type() {
        return type;
    }

    @Override
    public String title() {
        return title;
    }

    @Override
    public Serializable tag() {
        return tag;
    }
}
