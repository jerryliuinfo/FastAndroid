package com.apache.fastandroid.task

import android.text.TextUtils
import com.optimize.performance.launchstarter.task.Task
import com.tesla.framework.applike.FApplication
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.component.imageloader.IImageLoaderstrategy
import com.tesla.framework.component.imageloader.ImageLoaderManager
import com.apache.fastandroid.imageloader.GlideImageLoader

/**
 * Created by Jerry on 2021/4/17.
 */
class ImageLoaderTask:Task() {
    override fun run() {
        NLog.d("task", "ImageLoaderTask run --->")
        //初始化图片加载
      start()

    }


    companion object{
        @JvmStatic
        fun start(){
            NLog.d("task", "ImageLoaderTask run --->")
            ImageLoaderManager.getInstance().setImageLoaderStrategy(configImageLoader())
            ImageLoaderManager.getInstance().init(FApplication.getContext())
            Thread.sleep(120 )
        }

        private fun configImageLoader(): IImageLoaderstrategy {
            val imageLoaderClassName = "";
            return if (!TextUtils.isEmpty(imageLoaderClassName)) {
                try {
                    Class.forName(imageLoaderClassName).newInstance() as IImageLoaderstrategy
                } catch (e: Exception) {
                    GlideImageLoader()
                }
            } else GlideImageLoader()
            //如果没有配置，默认使用glide加载
        }
    }




}