package com.tesla.framework.common.util;

public class LaunchTimer {
    public static final String TAG = LaunchTimer.class.getSimpleName();

    private static long sTime;

    public static void startRecord() {
        sTime = System.currentTimeMillis();
    }

    public static void endRecord() {
        endRecord("");
    }

    public static void endRecord(String msg) {
        long cost = System.currentTimeMillis() - sTime;
        System.out.println(msg + "cost " + cost);
    }

}
