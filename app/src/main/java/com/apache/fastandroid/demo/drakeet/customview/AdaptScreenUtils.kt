package com.apache.fastandroid.demo.drakeet.customview

import android.content.res.Resources

object AdaptScreenUtils {

    @JvmStatic
    fun adaptWidth(resources: Resources, designWidth: Int): Resources {
        return resources.apply {
            val newXdpi = displayMetrics.widthPixels * 72f / designWidth
            displayMetrics.xdpi = newXdpi
        }
    }

    @JvmStatic
    fun adaptHeight(resources: Resources, designHeight: Int): Resources {
        return resources.apply {
            val newXdpi = displayMetrics.heightPixels * 72f / designHeight
            displayMetrics.xdpi = newXdpi
        }
    }

    @JvmStatic
    fun pt2Px(resource: Resources, ptValue: Float): Int {
        return (ptValue * resource.displayMetrics.xdpi / 72f + 0.5).toInt()
    }

    @JvmStatic
    fun px2Pt(resource: Resources, pxValue: Float): Int {
        return (pxValue * 72 / resource.displayMetrics.xdpi + 0.5).toInt()
    }
}