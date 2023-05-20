package com.tesla.framework.component.imageloader

import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * Created by Jerry on 2023/5/20.
 */


fun ImageView.display(url:String?, placeHolderRes:Int ?= null){
    Glide.with(context).load(url).apply {
        if (placeHolderRes != null){
            placeholder(placeHolderRes)
        }
    }.into(this)
}