package com.tesla.framework.component.imageloader

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.*
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import java.util.concurrent.ExecutionException
import kotlin.Int
import kotlin.String

/**
 * Created by Administrator on 2017/3/21 0021.
 */
class GlideImageLoader : IImageLoaderstrategy, IImagePahtFromCache {
    override fun showImage(imageView: ImageView, url: String?, options: ImageOptions?) {
        val mGenericRequestBuilder = init(imageView, url, options)
        showImageLast(imageView, mGenericRequestBuilder, options)
    }

    override fun cleanMemory(context: Context) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Glide.get(context).clearMemory()
        }
    }

    private fun getGenericRequestBuilder(
        manager: RequestManager,
        url: String?,
        options: ImageOptions?
    ): DrawableTypeRequest<*> {
        return if (!TextUtils.isEmpty(url)) {
            manager.load(url)
        } else manager.load(options!!.holderResId)
    }

    fun init(
        v: ImageView,
        url: String?,
        options: ImageOptions?
    ): GenericRequestBuilder<*, *, *, *> {
        val manager = Glide.with(v.context)
        var mDrawableTypeRequest: GenericRequestBuilder<*, *, *, *> =
            getGenericRequestBuilder(manager, url, options).asBitmap()
        //装载参数
        mDrawableTypeRequest = loadGenericParams(v, mDrawableTypeRequest, options)
        return mDrawableTypeRequest
    }

    private fun loadGenericParams(
        view: ImageView?,
        mGenericRequestBuilder: GenericRequestBuilder<*, *, *, *>,
        options: ImageOptions?
    ): GenericRequestBuilder<*, *, *, *> {
        var builder = mGenericRequestBuilder
        if (mGenericRequestBuilder is DrawableTypeRequest<*>) {
            if (options!!.isCrossFade!!) {
                mGenericRequestBuilder.crossFade()
            }
            if (options.asGif) {
                builder = mGenericRequestBuilder.asGif()
            }
        }
        builder.skipMemoryCache(options!!.skipMemoryCache!!)
        if (options.imageSize != null) {
            val width = getSize(options.imageSize.width, view)
            val height = getSize(options.imageSize.height, view)
            Log.i(
                "tag ",
                "load params " + options.imageSize.width + "  : " + options.imageSize.height
            )
            builder.override(width, height)
        }
        if (options.holderResId != null) {
            builder.placeholder(options.holderResId)
        }
        if (options.holderDrawable != null) {
            builder.placeholder(options.holderDrawable)
        }
        if (null != options.errorResId) {
            builder.error(options.errorResId)
        }
        if (options.errorDrawable != null) {
            builder.placeholder(options.errorDrawable)
        }
        if (options.mDiskCacheStrategy != null && options.mDiskCacheStrategy !== ImageOptions.DiskCacheStrategy.DEFAULT) {
            when (options.mDiskCacheStrategy) {
                ImageOptions.DiskCacheStrategy.NONE -> builder.diskCacheStrategy(
                    DiskCacheStrategy.NONE
                )
                ImageOptions.DiskCacheStrategy.All -> builder.diskCacheStrategy(DiskCacheStrategy.ALL)
                ImageOptions.DiskCacheStrategy.SOURCE -> builder.diskCacheStrategy(
                    DiskCacheStrategy.SOURCE
                )
                ImageOptions.DiskCacheStrategy.RESULT -> builder.diskCacheStrategy(
                    DiskCacheStrategy.RESULT
                )
                else -> {}
            }
        }
        return builder
    }

    private fun showImageLast(
        imageView: ImageView,
        mDrawableTypeRequest: GenericRequestBuilder<*, *, *, *>,
        options: ImageOptions?
    ) {
//        final ImageView img = (ImageView) options.getViewContainer();

        // 是否使用高斯模糊
        if (options?.blurImage == true) {
            // 具体的高斯模糊这里就不实现了，直接展示图片
            mDrawableTypeRequest.into(imageView)
            return
        }
        // 是否展示一个gif
        if (options?.asGif == true) {
            (mDrawableTypeRequest as GifRequestBuilder<*>).dontAnimate()
                .into(object : SimpleTarget<GifDrawable?>() {


                    override fun onResourceReady(
                        resource: GifDrawable?,
                        glideAnimation: GlideAnimation<in GifDrawable?>?
                    ) {
                        imageView.setImageDrawable(resource)
                        resource?.start()
                    }
                })
            return
        }

        mDrawableTypeRequest.into(imageView)
    }

    /**
     * 获取资源尺寸
     *
     * @param resSize
     * @return 默认返回原始尺寸
     */
    private fun getSize(resSize: Int, container: View?): Int {
        return if (resSize <= 0) {
            SimpleTarget.SIZE_ORIGINAL
        } else {
            try {
                container!!.context.resources.getDimensionPixelOffset(resSize)
            } catch (e: Resources.NotFoundException) {
                e.printStackTrace()
                Log.e("", "I got !!!")
                resSize
            }
        }
    }

    private var mContext: Context? = null
    override fun init(context: Context) {
        mContext = context
    }

    override fun pause(context: Context) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Glide.with(context).pauseRequests()
        }
    }

    override fun resume(context: Context) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Glide.with(context).resumeRequests()
        }
    }

    override fun getImagePahtFromCache(url: String?): String? {
        val futuer =
            Glide.with(mContext).load(url).downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
        try {
            val cacheFile = futuer.get()
            return cacheFile.absolutePath
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } catch (e: ExecutionException) {
            e.printStackTrace()
        }
        return null
    }

    override fun getBitmapFromCache(url: String?): Bitmap? {
        var myBitmap: Bitmap? = null
        try {
            myBitmap = Glide.with(mContext)
                .load(url)
                .asBitmap() //必须
                .centerCrop()
                .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .get()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } catch (e: ExecutionException) {
            e.printStackTrace()
        }
        return myBitmap
    }
}