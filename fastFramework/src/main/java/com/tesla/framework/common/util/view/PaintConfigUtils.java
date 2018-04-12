package com.tesla.framework.common.util.view;

import android.graphics.Paint;



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
