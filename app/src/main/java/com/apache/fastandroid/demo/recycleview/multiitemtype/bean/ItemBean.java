package com.apache.fastandroid.demo.recycleview.multiitemtype.bean;

/**
 * Author：岑胜德 on 2021/1/6 18:05
 * <p>
 * 说明：
 */
public class ItemBean implements IViewType {

    //所有Item类型都在这里定义
    public static final int TYPE_00 = 0;
    public static final int TYPE_01 = 1;
    public static final int TYPE_02 = 2;

    public int id;
    // Item类型标识（很关键！）
    private int viewType;


    //item具体业务数据字段
    public String text = "";


    public ItemBean(int viewType, String text) {
        this.viewType = viewType;
        this.text = text;
    }

    @Override
    public int viewType() {
        return viewType;
    }
}
