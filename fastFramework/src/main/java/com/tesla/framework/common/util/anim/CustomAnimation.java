package com.tesla.framework.common.util.anim;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

import com.chad.library.adapter.base.animation.BaseAnimation;

import androidx.annotation.NonNull;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class CustomAnimation implements BaseAnimation {


    @NonNull
    @Override
    public Animator[] animators(@NonNull View view) {
        return new Animator[]{
                ObjectAnimator.ofFloat(view, "scaleY", 1, 1.1f, 1),
                ObjectAnimator.ofFloat(view, "scaleX", 1, 1.1f, 1)
        };
    }
}
