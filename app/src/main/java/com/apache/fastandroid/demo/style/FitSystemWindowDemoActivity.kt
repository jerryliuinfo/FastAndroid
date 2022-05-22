package com.apache.fastandroid.demo.style

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.ActivityFitsSystemWindowBinding
import com.tesla.framework.kt.contentView
import kotlinx.android.synthetic.main.activity_layout_visualizer.*

/**
 * Created by Jerry on 2022/3/10.
 * CoordinatorLayout 设置 fitsSystemWindows="true"实现沉浸式状态栏
 * https://mp.weixin.qq.com/s/AiCNJAi9CgYDE1UuzCboGg
 */
class FitSystemWindowDemoActivity:AppCompatActivity() {

    private val mBinding:ActivityFitsSystemWindowBinding by contentView(R.layout.activity_fits_system_window)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //设置状态栏为透明
        window.statusBarColor = Color.TRANSPARENT

//        setContentView(R.layout.activity_fits_system_window)
    }
}