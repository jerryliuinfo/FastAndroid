package com.tesla.framework.common.util.anim;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

import com.chad.library.adapter.base.animation.BaseAnimation;

import androidx.annotation.NonNull;


/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class SlideInRightAnimation implements BaseAnimation {

    @NonNull
    @Override
    public Animator[] animators(@NonNull View view) {
        return new Animator[]{
                ObjectAnimator.ofFloat(view, "translationX", view.getRootView().getWidth(), 0)
        };
    }
}
