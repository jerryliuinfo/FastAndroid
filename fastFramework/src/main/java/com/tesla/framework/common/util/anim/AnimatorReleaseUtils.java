package com.tesla.framework.common.util.anim;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;

/**
 *
 */

public class AnimatorReleaseUtils {

    public static void releaseAnimator(Animator animator){
        if(animator!=null){
            animator.removeAllListeners();
            animator.cancel();
        }
    }
    public static void releaseAnimator(ValueAnimator animator){
        if(animator!=null){
            animator.removeAllListeners();
            animator.removeAllUpdateListeners();
            animator.cancel();
        }
    }
    public static void releaseAnimator(ObjectAnimator animator){
        if(animator!=null){
            animator.removeAllListeners();
            animator.removeAllUpdateListeners();
            animator.cancel();
        }
    }
}
