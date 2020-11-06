package com.apache.fastandroid.artemis.util;

import android.app.ActivityManager;
import android.content.Context;

import com.tesla.framework.Global;


public class HeapSizeUtil {
    private static final String TAG = "MemUtil";

    // 系统level<5的HeapSize默认值 单位M
    private static final int DEFAULT_HEAP_SIZE = 16;
    // 高低内存的界限 单位M
    private static final int THRESHOLD_LOW_HEAPSIZE = 64;
    // HeapSize
    private static int mHeapSize = 0;

    /**
     * 是否是低HeapSize
     *
     * @return
     */
    public static boolean isLowHeapSize() {
        return getHeapSize() < THRESHOLD_LOW_HEAPSIZE;
    }

    /**
     * 获取HeapSize
     *
     * @return
     */
    public static int getHeapSize() {
        // 大于0表示已经判断过
        if (mHeapSize > 0) {
            return mHeapSize;
        }
        ActivityManager am = (ActivityManager) Global.getSystemService(Context.ACTIVITY_SERVICE);
        mHeapSize = am.getMemoryClass();
        return mHeapSize;
    }
}
