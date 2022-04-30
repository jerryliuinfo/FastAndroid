package com.tesla.framework.component.imageloader

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import com.tesla.framework.common.util.DrawableUtils

class ImageLoader {
    // ...
    companion object {

        @JvmStatic
        fun load(imageView: ImageView, @DrawableRes resId: Int) {
            imageView.setImageResource(resId)
        }

        @JvmStatic
        fun load(view: View, @DrawableRes resId: Int) {
            view.setBackgroundResource(resId)
        }

        @JvmStatic
        fun load(view: View, @ColorInt color: Int, radius: Int = 0) {
            if (radius == 0) {
                view.setBackgroundColor(color)
            } else {
                view.background = DrawableUtils.createRadiusDrawable(color, radius)
            }
        }

        @JvmStatic
        fun load(view: View, colorString: String, radius: Int = 0) {
            load(view, Color.parseColor(colorString), radius)
        }
    }
}