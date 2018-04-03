package com.tesla.framework.support.bean;

import java.io.Serializable;

/**
 * Created by jerryliu on 2017/4/8.
 */

public class HeaderBean implements Serializable {

    public String title;

    public HeaderBean(String title) {
        this.title = title;
    }
}
