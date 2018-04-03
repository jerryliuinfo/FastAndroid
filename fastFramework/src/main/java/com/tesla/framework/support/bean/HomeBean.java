package com.tesla.framework.support.bean;

import com.tesla.framework.component.orm.annotation.AutoIncrementPrimaryKey;

import java.io.Serializable;

/**
 * Created by jerryliu on 2017/4/3.
 */

public class HomeBean implements Serializable {
    //服务器是没有这个id字段的
    @AutoIncrementPrimaryKey(column = "id")
    public String id;

    public String name;
    public String age;

    public HomeBean() {
    }

    public HomeBean(String name, String age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "HomeBean{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", age='" + age + '\'' + '}';
    }
}
