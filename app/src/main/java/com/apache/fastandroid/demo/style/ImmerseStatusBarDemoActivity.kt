package com.apache.fastandroid.demo.style

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import androidx.core.graphics.ColorUtils
import androidx.palette.graphics.Palette
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentImmerseStatusbarBinding
import com.tesla.framework.ui.activity.BaseVBActivity

/**
 * Created by Jerry on 2022/6/14.
 * 一个Android沉浸式状态栏上的黑科技:https://mp.weixin.qq.com/s/brZ6gog_4KYQaatmFtkAZA
 *
 */
class ImmerseStatusBarDemoActivity :
    BaseVBActivity<FragmentImmerseStatusbarBinding>(FragmentImmerseStatusbarBinding::inflate) {
    override fun layoutInit(savedInstanceState: Bundle?) {
        super.layoutInit(savedInstanceState)
        mBinding.darkImageBtn.setOnClickListener {
            setBgImageByResource(R.drawable.dark_image)
        }
        mBinding.lightImageBtn.setOnClickListener {
            setBgImageByResource(R.drawable.light_image)
        }
        mBinding.splitImageBtn.setOnClickListener {
            setBgImageByResource(R.drawable.split_image)
        }
        setBgImageByResource(R.drawable.split_image)
    }


    private fun setBgImageByResource(imageResource: Int) {
        val bitmap = BitmapFactory.decodeResource(resources, imageResource)
        mBinding.bgImage.setImageBitmap(bitmap)
        detectBitmapColor(bitmap)
    }

    private fun detectBitmapColor(bitmap: Bitmap) {
        val colorCount = 5
        val left = 0
        val top = 0
        val right = getScreenWidth()
        val bottom = getStatusBarHeight()

        Palette
            .from(bitmap)
            .maximumColorCount(colorCount)
            .setRegion(left, top, right, bottom)
            .generate {
                it?.let { palette ->
                    var mostPopularSwatch: Palette.Swatch? = null
                    for (swatch in palette.swatches) {
                        if (mostPopularSwatch == null
                            || swatch.population > mostPopularSwatch.population
                        ) {
                            mostPopularSwatch = swatch
                        }
                    }
                    mostPopularSwatch?.let { swatch ->
                        val luminance = ColorUtils.calculateLuminance(swatch.rgb)
                        // If the luminance value is lower than 0.5, we consider it as dark.
                        if (luminance < 0.5) {
                            setDarkStatusBar()
                        } else {
                            setLightStatusBar()
                        }
                    }
                }
            }
    }

    private fun setLightStatusBar() {
        val flags = window.decorView.systemUiVisibility
        window.decorView.systemUiVisibility = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    private fun setDarkStatusBar() {
        val flags = window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.decorView.systemUiVisibility = flags xor View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    private fun getScreenWidth(): Int {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.widthPixels
    }

    private fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }
}