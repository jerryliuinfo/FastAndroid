package com.apache.fastandroid.artemis.util;

import android.os.Build;

/**
 * 高性能手机判断  cpu频率+HeapSize+系统版本
 * 以后需要后台下发
 *
 */
public class PerformanceUtil {
    // 是否判断过
    private static boolean isEverJudge = false;
    // 是否是高端机
    private static boolean isHigh = false;


    private static boolean isEverJudgeForAsyncSave = false;

    private static boolean isHighForAsyncSave = false;

    /**
     * return false: cpu频率低 || sdk版本低 ||heapSize小
     * return true: other
     */
    public static boolean isHigh() {
        // 如果已经判断过直接返回结果
        if (isEverJudge) {
            return isHigh;
        }
        if (CpuUtil.isLowFreq() || HeapSizeUtil.isLowHeapSize()) {
            isHigh = false;
        } else {
            isHigh = true;
        }
        isEverJudge = true;
        return isHigh;
    }


    public static boolean isHighForAsyncSave() {
        // 如果已经判断过直接返回结果
        if (isEverJudgeForAsyncSave) {
            return isHighForAsyncSave;
        }
        if (isSDKUnderKITKAT()) {
            isHighForAsyncSave = false;
        } else {
            isHighForAsyncSave = true;
        }
        isEverJudgeForAsyncSave = true;
        return isHighForAsyncSave;
    }
    /**
     * 是否是4.4（不包括4.4）以下的系统
     */
    public static boolean isSDKUnderKITKAT() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT;
    }
}
