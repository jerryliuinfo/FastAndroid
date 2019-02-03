package com.apache.fastandroid.topic.video.bean;

import com.tesla.framework.network.biz.ResultBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jerryliu on 2017/4/11.
 */

public class VideoResultBean extends ResultBean implements Serializable {
    public int total;
    public List<VideoBean> videos;
}
