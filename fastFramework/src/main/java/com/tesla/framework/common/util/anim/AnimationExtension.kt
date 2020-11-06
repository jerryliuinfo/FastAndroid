package com.tesla.framework.common.util.anim

import android.animation.Animator

/**
 * author: jerry
 * created on: 2020/9/18 12:40 PM
 * description:
 */

fun Animator.release(){
    this?.let {
        it.removeAllListeners()
        it.cancel()
    }
}

