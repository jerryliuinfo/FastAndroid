package com.tesla.framework.component.imageloader

import android.graphics.Bitmap

/**
 * Created by 01370340 on 2017/11/21.
 */
interface IImagePahtFromCache {

    fun getImagePahtFromCache(url: String?): String?

    fun getBitmapFromCache(url: String?): Bitmap?
}