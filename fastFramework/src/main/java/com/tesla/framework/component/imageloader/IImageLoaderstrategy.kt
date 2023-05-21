package com.tesla.framework.component.imageloader

import android.content.Context
import android.widget.ImageView

/**
 * Created by Administrator on 2017/3/20 0020.
 */
interface IImageLoaderstrategy {

    fun showImage(imageView: ImageView, url: String?, options: ImageOptions?= null)

    fun cleanMemory(context: Context)

    /**
     * 有些图片框架显示图片需要做一些初始化配置
     * @param context
     */
    fun init(context: Context)
    fun pause(context: Context)
    fun resume(context: Context)
}