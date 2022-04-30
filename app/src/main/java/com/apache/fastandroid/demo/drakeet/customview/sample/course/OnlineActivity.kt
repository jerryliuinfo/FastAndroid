package com.apache.fastandroid.demo.drakeet.customview.sample.course

import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.apache.fastandroid.demo.drakeet.customview.AdaptScreenUtils

class OnlineActivity : AppCompatActivity() {

    override fun getResources(): Resources {
        val rawResources = super.getResources()
        return if (rawResources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            AdaptScreenUtils.adaptWidth(rawResources, MOBILE_DESIGN_WIDTH_IN_DP)
        } else {
            AdaptScreenUtils.adaptHeight(rawResources, MOBILE_DESIGN_WIDTH_IN_DP)
        }
    }


    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        val contentView = OnlineActivityLayout(this)
        setContentView(contentView)
    }

    companion object {
        private const val MOBILE_DESIGN_WIDTH_IN_DP = 375
    }
}