package com.apache.fastandroid.artemis.util;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import static android.animation.ValueAnimator.*;

/**
 * Created by winghe on 2016/3/24.
 */
public class AnimateUtil {
    public static Animator x(View view, int from, int to) {
        return ObjectAnimator.ofFloat(view, "x", from, to);
    }

    public static Animator y(View view, int from, int to) {
        return ObjectAnimator.ofFloat(view, "y", from, to);
    }

    public static Animator xy(View view, int[] fromXY, int[] toXY) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(x(view, fromXY[0], toXY[0]), y(view, fromXY[1], toXY[1]));
        return animatorSet;
    }

    public static Animator alpha(View view, float... values) {
        return ObjectAnimator.ofFloat(view, "alpha", values);
    }

    @SuppressLint("WrongConstant")
    public static Animator rotation(View view, int from, int to) {
        ObjectAnimator rotation = ObjectAnimator.ofFloat(view, "rotation", from, to);
        rotation.setRepeatMode(INFINITE);
        return rotation;
    }

    public static Animator scale(View view, float... values) {
        AnimatorSet animatorSet = new AnimatorSet();
        Animator animatorX = ObjectAnimator.ofFloat(view, "scaleX", values);
        Animator animatorY = ObjectAnimator.ofFloat(view, "scaleY", values);
        animatorSet.playTogether(animatorX, animatorY);
        return animatorSet;
    }

    /**
     *
     * @param scaleType 1 宽， 2 高， 其他 宽高
     * @return
     */
    public static ValueAnimator scaleByLp(final View view, int duration, final int scaleType, float... values) {
        if (view == null || view.getLayoutParams() == null) {
            return null;
        }
        ValueAnimator scale = ofFloat(values);
        LayoutParams vlp = view.getLayoutParams();
        if (vlp == null) {
            vlp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        }
        final LayoutParams lp = vlp;
        final int width = view.getWidth();
        final int height = view.getHeight();
        scale.addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (scaleType == 1) {
                    lp.width = (int)((float)width * (Float)animation.getAnimatedValue());
                } else if (scaleType == 2) {
                    lp.height = (int)((float)height * (Float)animation.getAnimatedValue());
                } else {
                    lp.width = (int)((float)width * (Float)animation.getAnimatedValue());
                    lp.height = (int)((float)height * (Float)animation.getAnimatedValue());
                }
                view.setLayoutParams(lp);
            }
        });
        scale.setDuration(duration);
        scale.start();
        return scale;
    }

}
