package com.apache.fastandroid.demo.glide

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.BaseStatusFragmentNew
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import kotlinx.android.synthetic.main.glide_callback_listen_demo.*

/**
 * Created by Jerry on 2021/6/24.
 */
class GlideCallbackListenFragment: BaseStatusFragmentNew() {
    override fun getLayoutId(): Int {
        return R.layout.glide_callback_listen_demo
    }
    val url = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fcdn.duitang.com%2Fuploads%2Fblog%2F201406%2F12%2F20140612042459_nN5mZ.jpeg&refer=http%3A%2F%2Fcdn.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1627090612&t=376413dceca7371b4b2086cdbb955ff2"
    val preLoadUrl = "https://c-ssl.duitang.com/uploads/blog/202009/30/20200930092302_00840.jpeg"

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        btn_custom_target.setOnClickListener {
            Glide.with(activity).load(preLoadUrl).into(simpleTarget)
        }

        btn_bitmap_target.setOnClickListener {
            Glide.with(activity).load(url).asBitmap().into(simpleBitmapTarget)
        }
        btn_view_target.setOnClickListener {
//            Glide.with(activity).load(url).into(mylayout.viewTarget)
        }

        btn_pre_load.setOnClickListener {
            Glide.with(activity).load(preLoadUrl).diskCacheStrategy(DiskCacheStrategy.SOURCE).preload()
        }

        btn_pre_load.setOnClickListener {
            Glide.with(activity).load(preLoadUrl).diskCacheStrategy(DiskCacheStrategy.SOURCE).preload()
        }

    }

    private val simpleTarget = object :SimpleTarget<GlideDrawable>(){
        override fun onResourceReady(resource: GlideDrawable?, glideAnimation: GlideAnimation<in GlideDrawable>?) {
            imageview.setImageDrawable(resource)
        }
    }

    /**
     * 如果可以确定加载的是静态图片，而不是gif，可以拿到Bitmap对象
     */
    private val simpleBitmapTarget = object :SimpleTarget<Bitmap>(){
        override fun onResourceReady(resource: Bitmap?, glideAnimation: GlideAnimation<in Bitmap>?) {
            imageview.setImageBitmap(resource)
        }
    }


}