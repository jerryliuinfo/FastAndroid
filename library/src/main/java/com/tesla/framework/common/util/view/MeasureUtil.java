package com.tesla.framework.common.util.view;

import android.view.View;

/**
 * Created by jerryliu on 2017/6/14.
 */

public class MeasureUtil {
    /**
     * 自定义View支持wrap属性
     * @param measureSpec
     * @param defaultSize
     * @return
     */
    public static int getMeasuredLength(int measureSpec, int defaultSize){
        int measureSize = View.MeasureSpec.getSize(measureSpec);
        int measureMode = View.MeasureSpec.getMode(measureSpec);
        if (measureMode == View.MeasureSpec.EXACTLY){
            return measureSize;
        }
        return defaultSize;
    }
}
