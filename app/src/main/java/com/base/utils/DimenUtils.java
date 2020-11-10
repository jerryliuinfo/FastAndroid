package com.base.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.apache.fastandroid.app.FastAndroidApplication;

/**
 * @author Jerry
 * @Description:
 * @date 2016/7/19 19:34
 * @copyright TCL-MIG
 */

public class DimenUtils {


    private static final int DP_TO_PX = TypedValue.COMPLEX_UNIT_DIP;
    private static final int SP_TO_PX = TypedValue.COMPLEX_UNIT_SP;
    private static final int PX_TO_DP = TypedValue.COMPLEX_UNIT_MM + 1;
    private static final int PX_TO_SP = TypedValue.COMPLEX_UNIT_MM + 2;
    private static final int DP_TO_PX_SCALE_H = TypedValue.COMPLEX_UNIT_MM + 3;
    private static final int DP_SCALE_H = TypedValue.COMPLEX_UNIT_MM + 4;
    private static final int DP_TO_PX_SCALE_W = TypedValue.COMPLEX_UNIT_MM + 5;

    // -- dimens convert

    private static float applyDimension(Context context, int unit, float value, DisplayMetrics metrics) {
        switch (unit) {
            case DP_TO_PX:
            case SP_TO_PX:
                return TypedValue.applyDimension(unit, value, metrics);
            case PX_TO_DP:
                return value / metrics.density;
            case PX_TO_SP:
                return value / metrics.scaledDensity;
            case DP_TO_PX_SCALE_H:
                return TypedValue.applyDimension(DP_TO_PX, value * getScaleFactorH(context), metrics);
            case DP_SCALE_H:
                return value * getScaleFactorH(context);
            case DP_TO_PX_SCALE_W:
                return TypedValue.applyDimension(DP_TO_PX, value * getScaleFactorW(context), metrics);
        }
        return 0;
    }
    public static int dp2px(float value) {
        return (int) applyDimension(FastAndroidApplication.getContext(), DP_TO_PX, value, FastAndroidApplication.getContext().getResources().getDisplayMetrics());
    }

    public static int dp2px(Context context, float value) {
        return (int) applyDimension(context, DP_TO_PX, value, context.getResources().getDisplayMetrics());
    }
    public static int px2sp(Context context, float value) {
        return (int) applyDimension(context, PX_TO_SP, value, context.getResources().getDisplayMetrics());
    }

    public final static float BASE_SCREEN_WIDH = 720f;
    public final static float BASE_SCREEN_HEIGHT = 1280f;
    public final static float BASE_SCREEN_DENSITY = 2f;
    public static Float sScaleW, sScaleH;


    /**
     * 如果要计算的值已经经过dip计算，则使用此结果，如果没有请使用getScaleFactorWithoutDip
     */
    public static float getScaleFactorW(Context context) {
        if (sScaleW == null) {
            sScaleW = (getScreenWidth(context) * BASE_SCREEN_DENSITY) / (getDensity(context) * BASE_SCREEN_WIDH);
        }
        return sScaleW;
    }

    public static float getScaleFactorH(Context context) {
        if (sScaleH == null) {
            sScaleH = (getScreenHeight(context) * BASE_SCREEN_DENSITY)
                    / (getDensity(context) * BASE_SCREEN_HEIGHT);
        }
        return sScaleH;
    }

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
     *
     * @return 屏幕英寸数
     */
    public static double getDiagonalInch(Context context) {
        int width = context.getResources().getDisplayMetrics().widthPixels;  // 屏幕宽度（像素）
        int height = context.getResources().getDisplayMetrics().heightPixels;  // 屏幕高度（像素）
        int densityDpi = context.getResources().getDisplayMetrics().densityDpi;  // 屏幕密度DPI（120 / 160 / 240）
        double screenSize = Math.sqrt(Math.pow(width, 2) + Math.pow(height, 2)) / densityDpi;
        return screenSize;
    }


    public static int getWindowHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int dp2px(Context context, float dp, float dentity) {
        float currentD = context.getResources().getDisplayMetrics().density;
        return (int) (dp * currentD / dentity + 0.5f);
    }


}
