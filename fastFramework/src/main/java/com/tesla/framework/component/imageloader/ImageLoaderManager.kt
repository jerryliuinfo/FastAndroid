package com.tesla.framework.component.imageloader

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import com.tesla.framework.common.util.DrawableUtils

/**
 * Created by Administrator on 2017/3/22 0022.
 */
object ImageLoaderManager : IImagePahtFromCache {
    private var loaderstrategy: IImageLoaderstrategy = GlideImageLoader()
    private val DEFAULT_OPTION = ImageOptions()
    private var mOptions = DEFAULT_OPTION

    /**
     *
     * 如果需要设置一些默认的参数,可以在这里设置
     * @param options
     */
    fun setOptions(options: ImageOptions) {
        this.mOptions = options
    }

    fun setImageLoaderStrategy(strategy: IImageLoaderstrategy) {
        loaderstrategy = strategy
    }

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


    fun loadImage(imageView: ImageView, url: String?, options: ImageOptions? = null) {
        loaderstrategy.showImage(imageView, url, options ?: mOptions)


    }




    fun cleanMemory(context: Context) {
        loaderstrategy.cleanMemory(context)
    }

    fun init(context: Context) {
        loaderstrategy.init(context)
    }

    fun pause(context: Context) {}

    fun resume(context: Context) {}


    override fun getImagePahtFromCache(url: String?): String? {
        if (loaderstrategy is IImagePahtFromCache) {
            val iImagePahtFromCache = loaderstrategy as IImagePahtFromCache
            return iImagePahtFromCache.getImagePahtFromCache(url)
        }
        return null
    }

    override fun getBitmapFromCache(url: String?): Bitmap? {
        if (loaderstrategy is IImagePahtFromCache) {
            val iImagePahtFromCache = loaderstrategy as IImagePahtFromCache
            return iImagePahtFromCache.getBitmapFromCache(url)
        }


        return null
    }


}