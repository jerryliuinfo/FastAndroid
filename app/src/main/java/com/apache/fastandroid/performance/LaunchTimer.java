package com.apache.fastandroid.performance;

import com.apache.fastandroid.util.MainLogUtil;

public class LaunchTimer {

    private static long sTime;

    public static void startRecord() {
        sTime = System.currentTimeMillis();
    }

    public static void xendRecord() {
        endRecord("");
    }

    public static void endRecord(String msg) {
        long interval = System.currentTimeMillis() - sTime;
        MainLogUtil.d(String.format("%s cost %s ms", msg, interval));
    }

}
