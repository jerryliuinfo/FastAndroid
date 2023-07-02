package com.tesla.framework.component.imageloader

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter

/**
 * Created by Jerry on 2023/5/20.
 */


fun ImageView.showImage(url:String?, placeHolderRes:Int ?= null){

    ImageLoaderManager.loadImage(this,url)
}


@BindingAdapter(
    "glideSrc",
    "glideCenterCrop",
    "glideCircularCrop",
    requireAll = false
)
fun ImageView.bindGlideSrc(
    @DrawableRes drawableRes: Int?,
    centerCrop: Boolean = false,
    circularCrop: Boolean = false
) {
    if (drawableRes == null) return

    ImageLoaderManager.load(this,drawableRes)
}
