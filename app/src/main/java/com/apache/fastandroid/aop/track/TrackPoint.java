package com.apache.fastandroid.aop.track;

/**
 * author: 01370340
 * data: 2019/7/15
 * description:
 */
public class TrackPoint {
    private static TrackPointCallBack trackPointCallBack;

    private TrackPoint() {
    }

    public static void init(TrackPointCallBack callBack) {
        trackPointCallBack = callBack;
    }

    public static void onClick(String pageClassName, String viewIdName) {
        if (trackPointCallBack == null) {
            return;
        }
        trackPointCallBack.onClick(pageClassName, viewIdName);
    }

    public static void onPageOpen(String pageClassName) {
        if (trackPointCallBack == null) {
            return;
        }
        trackPointCallBack.onPageOpen(pageClassName);
    }

    public static void onPageClose(String pageClassName) {
        if (trackPointCallBack == null) {
            return;
        }
        trackPointCallBack.onPageClose(pageClassName);
    }
}
