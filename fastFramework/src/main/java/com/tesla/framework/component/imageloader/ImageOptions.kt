package com.tesla.framework.component.imageloader

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes

/**
 * Created by Jerry on 2023/5/20.
 */
data class ImageOptions(
    @JvmField
    @DrawableRes val holderResId: Int? = -1,
    @JvmField
    val holderDrawable: Drawable? = null,



    val errorResId: Int? = -1,
    @JvmField
    val errorDrawable: Drawable? = null,
    @JvmField
    val imageSize: ImageSize? = null,
    val isCrossFade: Boolean? = false,
    @JvmField
    val skipMemoryCache: Boolean? = false,
    @JvmField
    val asGif: Boolean = false,
    @JvmField
    val blurImage: Boolean? = false,
    @JvmField

    val mDiskCacheStrategy: DiskCacheStrategy? = null,
    @JvmField

    val type: ImageType? = null,
    @JvmField

    val wifiStrategy: Boolean? = false, //加载策略，是否在wifi下才加载
    @JvmField

    val crossFade: Boolean = false


) {

    //对应重写图片size
    data class ImageSize(val width: Int, val height: Int)
    enum class DiskCacheStrategy {
        All,  //表示既缓存原始图片，也缓存转换过后的图片
        NONE,  //表示不缓存任何内容
        SOURCE,  //表示只缓存原始图片
        RESULT,  //表示只缓存转换过后的图片（默认选项）
        DEFAULT
    }

    enum class ImageType {
        PIC_LARGE, PIC_MEDIUM, PIC_SMALL
    }
}




