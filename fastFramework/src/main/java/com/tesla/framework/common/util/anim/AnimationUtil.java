package com.tesla.framework.common.util.anim;

import android.animation.ValueAnimator;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorListener;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;


public class AnimationUtil {
    public static final String PROPERTY_TRANSLATION_X = "translationX";

    public static final String PROPERTY_TRANSLATION_Y = "translationY";

    public static final String  PROPERTY_ROTATION = "rotation";

    public static final String  PROPERTY_ALPHA = "alpha";

    public static final String PROPERTY_SCALE_X = "scaleX";

    public static final String PROPERTY_SCALE_y = "scaleY";

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


    public static  final String TAG="Animator===";

    public static final LinearOutSlowInInterpolator FAST_OUT_SLOW_IN_INTERPOLATOR = new LinearOutSlowInInterpolator();

    // 显示view
    public static void scaleShow(View view, ViewPropertyAnimatorListener viewPropertyAnimatorListener) {
        view.setVisibility(View.VISIBLE);
        ViewCompat.animate(view)
                .scaleX(1.0f)
                .scaleY(1.0f)
                .alpha(1.0f)
                .setDuration(500)
                .setListener(viewPropertyAnimatorListener)
                .setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR)
                .start();
    }

    // 隐藏view
    public static void scaleHide(View view, ViewPropertyAnimatorListener viewPropertyAnimatorListener) {
        ViewCompat.animate(view)
                .scaleX(0.0f)
                .scaleY(0.0f)
                .alpha(0.0f)
                .setDuration(1500)
                .setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR)
                .setListener(viewPropertyAnimatorListener)
                .start();
    }

    public static void tanslation(final View view, float  start, float end){
        final ValueAnimator animator= ValueAnimator.ofFloat(start,end);
        view.setVisibility(View.VISIBLE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Float value = (Float) animator.getAnimatedValue();
                Log.i(TAG, "tanslation: value="+value);
                view.setTranslationY(value);
            }
        });
        animator.setDuration(200);
        animator.setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR);
        animator.start();
    }

    public static void showHeight(final View view, float  start, float end){
        final ValueAnimator animator= ValueAnimator.ofFloat(start,end);
        final ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (Float) animator.getAnimatedValue();
                layoutParams.height=(int) value;
                view.setLayoutParams(layoutParams);
                Log.i(TAG, "onAnimationUpdate: value="+value);

            }
        });
        animator.setDuration(500);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.start();
    }

    public static void show(final View view, int  start, int end){
        final int height = view.getHeight();
        final ValueAnimator animator= ValueAnimator.ofInt(start,end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (Integer) animator.getAnimatedValue();
                Log.i(TAG, "onAnimationUpdate: value="+value);
                view.setTop(value);
                view.setBottom(value+height);
            }
        });
        view.setVisibility(View.VISIBLE);
        animator.setDuration(200);
        animator.setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR);
        animator.start();
    }
}
