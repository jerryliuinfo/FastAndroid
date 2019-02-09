package com.tesla.framework.support.bean;

/**
 * Created by Jerry on 2019/2/9.
 */
public class InterceptorBean {
    private Object tag;

    public InterceptorBean() {
    }

    public InterceptorBean(Object tag) {
        this.tag = tag;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }
}
