package com.apache.fastandroid.support.bean;

import java.io.Serializable;

/**
 * Created by 01370340 on 2018/11/7.
 */
public class StandardPkgBean implements Serializable {

    private String name;

    private int num;

    private boolean isSelected;

    public StandardPkgBean() {
    }

    public StandardPkgBean(String name, int num) {
        this.name = name;
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("StandardPkgBean{");
        sb.append("name='").append(name).append('\'');
        sb.append(", num=").append(num);
        sb.append('}');
        return sb.toString();
    }
}
