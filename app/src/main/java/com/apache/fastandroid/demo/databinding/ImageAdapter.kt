package com.apache.fastandroid.demo.databinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.tesla.framework.component.imageloader.ImageLoaderManager

/**
 * Created by Jerry on 2021/1/22.
 */
class ImageAdapter {
    companion object{

        @JvmStatic
        @BindingAdapter(value = ["bind:url"], requireAll = false)
        fun displayImage(imageView: ImageView, url:String){
            ImageLoaderManager.getInstance().showImage(imageView,url)
        }
    }
}