package com.chad.baserecyclerviewadapterhelper.entity;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class HomeItem {
    private String title;
    private Class<?> activity;
    private int imageResource;

    public HomeItem() {
    }

    public HomeItem(String title, Class<?> activity, int imageResource) {
        this.title = title;
        this.activity = activity;
        this.imageResource = imageResource;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Class<?> getActivity() {
        return activity;
    }

    public void setActivity(Class<?> activity) {
        this.activity = activity;
    }
}