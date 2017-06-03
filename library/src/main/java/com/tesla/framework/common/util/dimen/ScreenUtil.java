package com.tesla.framework.common.util.dimen;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by jerryliu on 2017/6/3.
 */

public class ScreenUtil {
    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    public static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }


    /**
     * 获取屏幕英寸数
     * @return 屏幕英寸数
     */
    public static double getDiagonalInch(Context context)
    {
        int width = context.getResources().getDisplayMetrics().widthPixels;  // 屏幕宽度（像素）
        int height = context.getResources().getDisplayMetrics().heightPixels;  // 屏幕高度（像素）
        int densityDpi = context.getResources().getDisplayMetrics().densityDpi;  // 屏幕密度DPI（120 / 160 / 240）
        double screenSize = Math.sqrt(Math.pow(width, 2)+Math.pow(height, 2))/densityDpi;
        return screenSize;
    }
}
