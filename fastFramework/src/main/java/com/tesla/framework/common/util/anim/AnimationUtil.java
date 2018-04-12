package com.tesla.framework.common.util.anim;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;


public class AnimationUtil {

    /**
     * 控件向左滑动的动画
     * @param v 启动动画的控件
     * @param al 动画监听器
     */
    public static void translationX(View v, Animation.AnimationListener al, long durationMillis) {
        if(v==null) return;
        int initialWidth = v.getMeasuredWidth();
        TranslateAnimation transAni=new TranslateAnimation(0.0f, -initialWidth,0.0f,0.0f);
        if (al!=null) {
            //设置监听器
            transAni.setAnimationListener(al);
        }
        transAni.setDuration(durationMillis);
        v.startAnimation(transAni);
    }

    /**
     * 控件向右滑动的动画
     * @param v 启动动画的控件
     * @param al 动画监听器
     */
    public static void translationXRight(View v, Animation.AnimationListener al, long durationMillis) {
        if(v==null) return;
        int initialWidth = v.getMeasuredWidth();
        TranslateAnimation transAni=new TranslateAnimation(0.0f, initialWidth,0.0f,0.0f);
        if (al!=null) {
            //设置监听器
            transAni.setAnimationListener(al);
        }
        transAni.setDuration(durationMillis);
        v.startAnimation(transAni);
    }

    /**
     * 控件向左滑动的动画,滑动后停留在滑动后的位置
     * @param v 启动动画的控件
     * @param al 动画监听器
     */
    public static void transXFillAfter(View v, Animation.AnimationListener al, long durationMillis) {
        if(v==null) return;
        int initialWidth = v.getMeasuredWidth();
        TranslateAnimation transAni=new TranslateAnimation(0.0f, -initialWidth,0.0f,0.0f);
        if (al!=null) {
            //设置监听器
            transAni.setAnimationListener(al);
        }
        transAni.setFillAfter(true);
        transAni.setDuration(durationMillis);
        v.startAnimation(transAni);
    }
}
