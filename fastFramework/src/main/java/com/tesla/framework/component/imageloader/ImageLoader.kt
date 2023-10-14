package com.tesla.framework.component.imageloader

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.Px
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.tesla.framework.R

/**
 * Created by Jerry on 2023/10/14.
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

