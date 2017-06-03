package com.tesla.framework.common.util.view;

import android.graphics.Paint;

/**
 * @author wangtianbao
 * @Description:
 * @date 2016/11/4 13:59
 * @copyright TCL-MIG
 */

public class PaintConfigUtils {

    public static void configNormal(Paint paint, int color, float strokeWidth, Paint.Style style){
        paint.setColor(color);
        paint.setStyle(style);
        paint.setStrokeWidth(strokeWidth);
    }

    //线条圆化
    public static void configRound(Paint paint){
        paint.setDither(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
    }
}
