package com.tesla.framework.support.skin;

/**
 * Created by Jerry on 2020-04-28.
 */
public class SkintItem {
    //backgournd、textColor
    String name;
    //属性的值id @color/colorPrimary
    int resId;

    //属性值的类型 color、 mipmap
    String typeName;

    //属性的值的名字 colorPrimary
    String entryName;

    public SkintItem(String name, int resId, String entryName, String typeName) {
        this.name = name;
        this.resId = resId;
        this.entryName = entryName;
        this.typeName = typeName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getEntryName() {
        return entryName;
    }

    public void setEntryName(String entryName) {
        this.entryName = entryName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }



}
