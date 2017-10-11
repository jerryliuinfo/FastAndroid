package com.apache.fastandroid.novel.find.bean;

import java.io.Serializable;

/**
 * Created by 01370340 on 2017/10/10.
 */

public class BookBean implements Serializable{
    public String _id;
    public String title;
    public String author;
    public String shortIntro;
    public String cover;
    public String site;
    public String cat;
    public int banned;
    public int latelyFollower;
    public int latelyFollowerBase;
    public String minRetentionRatio;
    public String retentionRatio;
    public String majorCate;


    public BookBean() {
    }

    public BookBean(String _id, String cover, String title, String author, String majorCate, String shortIntro, int latelyFollower, String retentionRatio) {
        this._id = _id;
        this.cover = cover;
        this.title = title;
        this.author = author;
        this.majorCate = majorCate;
        this.shortIntro = shortIntro;
        this.latelyFollower = latelyFollower;
        this.retentionRatio = retentionRatio;
    }
}
