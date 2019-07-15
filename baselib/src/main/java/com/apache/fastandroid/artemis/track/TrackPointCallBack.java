package com.apache.fastandroid.artemis.track;

/**
 * author: 01370340
 * data: 2019/7/15
 * description:
 */
public interface TrackPointCallBack {

    void onPageOpen(String pageClassName);

    void onPageClose(String pageClassName);

    void onClick(String pageClassName, String viewIdName);
}
