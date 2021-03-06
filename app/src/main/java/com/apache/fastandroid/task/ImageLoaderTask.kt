package com.apache.fastandroid.task

import android.text.TextUtils
import com.optimize.performance.launchstarter.task.Task
import com.tesla.framework.applike.FrameworkApplication
import com.tesla.framework.common.setting.SettingUtility
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
        val loaderstrategy: IImageLoaderstrategy = configImageLoader()
        if (loaderstrategy != null) {
            ImageLoaderManager.getInstance().setImageLoaderStrategy(loaderstrategy)
            ImageLoaderManager.getInstance().init(FrameworkApplication.getContext())
        }
    }


    private fun configImageLoader(): IImageLoaderstrategy {
        val imageLoaderClassName = SettingUtility.getStringSetting("imageLoader")
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