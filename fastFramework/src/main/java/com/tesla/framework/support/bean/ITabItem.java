package com.tesla.framework.support.bean;

import java.io.Serializable;

/**
 * Created by Jerry on 2021/6/29.
 */
public interface ITabItem {
    String type();
    String title();
    Serializable tag();
}
