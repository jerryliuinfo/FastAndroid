package com.apache.fastandroid.demo.glide

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.apache.fastandroid.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.tesla.framework.applike.FrameworkApplication
import com.tesla.framework.common.util.ColorUtil
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.ui.fragment.BaseFragment
import kotlinx.android.synthetic.main.glide_basic_usage_demo.*

/**
 * Created by Jerry on 2021/6/24.
 */
class GlideBasicUsageFragment: BaseFragment() {
    override fun inflateContentView(): Int {
        return R.layout.glide_basic_usage_demo
    }
//    val url = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fcdn.duitang.com%2Fuploads%2Fblog%2F201406%2F12%2F20140612042459_nN5mZ.jpeg&refer=http%3A%2F%2Fcdn.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1627090612&t=376413dceca7371b4b2086cdbb955ff2"
    val url = "https://img.static-ottera.com/prod/tg/show/thumbnails/widescreen/omnom_s17_banner_8.jpg"

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        btn.setOnClickListener {
            Glide.with(activity).load(url)
                    .listener(navHeaderBgLoadListener)
                    .into(image)
        }
        btn_disable_cache.setOnClickListener {
            Glide.with(activity).load(url)
                    //不缓存图片
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .placeholder(R.drawable.sample_footer_loading)
                    .into(image)
        }
        btn_dimension.setOnClickListener {
            Glide.with(activity).load(url)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .placeholder(R.drawable.sample_footer_loading)
                    //glide只会将图片加载成100*100读到内存中(和显示的大小不是一个东西),而不管ImageView的大小是多少
                    .override(100, 100)
                    .into(image)
        }
        btn_origin_size.setOnClickListener {
            Glide.with(activity).load(url)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .placeholder(R.drawable.sample_footer_loading)
                    //glide只会将图片加载成100*100,而不管ImageView的大小是多少
                    .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .into(image)
        }
    }


    private var navHeaderBgLoadListener: RequestListener<Any, GlideDrawable> = object : RequestListener<Any, GlideDrawable> {

        override fun onException(e: Exception?, model: Any, target: Target<GlideDrawable>, isFirstResource: Boolean): Boolean {
            e?.printStackTrace()
            NLog.d(TAG, "onException")
            return false
        }

        override fun onResourceReady(glideDrawable: GlideDrawable?, model: Any, target: Target<GlideDrawable>,
                                     isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {

            return false
        }
    }
}