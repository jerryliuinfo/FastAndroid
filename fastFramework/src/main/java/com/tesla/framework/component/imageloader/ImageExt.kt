package com.tesla.framework.component.imageloader

import android.widget.ImageView

/**
 * Created by Jerry on 2023/5/20.
 */


fun ImageView.showImage(url:String?, placeHolderRes:Int ?= null){

    ImageLoaderManager.loadImage(this,url)
}