package com.tesla.framework.common.util

import android.graphics.drawable.GradientDrawable
import androidx.annotation.ColorInt

object DrawableUtils {

    @JvmStatic
    fun createRadiusDrawable(
        @ColorInt color: Int,
        topLeft: Int = 0,
        topRight: Int = 0,
        bottomRight: Int = 0,
        bottomLeft: Int = 0,
    ) = GradientDrawable().apply {
        setColor(color)
        cornerRadii = floatArrayOf(
            topLeft.toFloat(), topLeft.toFloat(),
            topRight.toFloat(), topRight.toFloat(),
            bottomRight.toFloat(), bottomRight.toFloat(),
            bottomLeft.toFloat(), bottomLeft.toFloat()
        )
    }

    @JvmStatic
    fun createRadiusDrawable(@ColorInt color: Int, radius: Int) =
        createRadiusDrawable(color, radius, radius, radius, radius)
}