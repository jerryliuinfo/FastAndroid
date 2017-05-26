package com.tesla.framework.common.util.anim;

import android.animation.ObjectAnimator;
import android.view.View;

/**
 * Created by jerryliu on 2017/5/18.
 */

public class AnimUtil {
    public static ObjectAnimator getAlphaAnimator(View view){
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, View.ALPHA,0.0f,1.0f);
        return animator;
    }



}
