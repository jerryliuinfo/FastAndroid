package com.apache.fastandroid.topic.video.bean;

import java.io.Serializable;

/**
 * Created by jerryliu on 2017/4/11.
 */

public class VideoBean implements Serializable {
    public String id;
    public String title;
    public String link;
    public String thumbnail;
    public String thumbnail_v2;
    public String duration;
    public String category;
    public String tags;
    public String state;
    public int view_count;
    public int favorite_count;
    public int comment_count;
    public int up_count;
    public int down_count;
    public String published;
    public VideoUserBean user;
    public String public_type;
    public String paid;
}
