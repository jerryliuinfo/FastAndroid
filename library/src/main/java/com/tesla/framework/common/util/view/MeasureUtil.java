package com.tesla.framework.common.util.view;

import android.graphics.Paint;
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
        int result = defaultSize;
        int specSize = View.MeasureSpec.getSize(measureSpec);
        int specMode = View.MeasureSpec.getMode(measureSpec);
        if (specMode == View.MeasureSpec.EXACTLY){
            result = specSize;
        }else if (specMode == View.MeasureSpec.AT_MOST){
            result = Math.min(result,specSize);
        }
        return result;
    }

    /**
     * 测量文字高度
     * @param paint
     * @return
     */
    public static float measureTextHeight(Paint paint) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        return (Math.abs(fontMetrics.ascent) - fontMetrics.descent);
    }
}
