package com.tesla.framework.common.util;

import android.content.Context;

import com.tesla.framework.FrameworkApplication;

/**
 * Created by jerryliu on 2017/4/11.
 */

public class DimensUtil {


    /**
     * getDimensionPixelOffset 获取dimension，如果resId为sp或者dp，就进行换算，如果是px，直接使用
     * @param resId
     * @return
     */
    public static int getDimensionPixelOffset(int resId) {
        Context context = FrameworkApplication.getContext();
        int dimension = 0;
        try {
            dimension = context.getResources().getDimensionPixelOffset(resId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dimension;
    }


    public static int dip2px(float dp){
        Context context = FrameworkApplication.getContext();
        int px = (int) (dp + 0.5f);
        try {
            px = (int) (context.getResources().getDisplayMetrics().density * dp + 0.5f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return px;
    }

    public static int sp2px(int sp){
        Context context = FrameworkApplication.getContext();
        int px = (int)(sp + 0.5f);
        try {
            px = (int)(context.getResources().getDisplayMetrics().scaledDensity * sp + 0.5f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return px;
    }
}
