package com.apache.fastandroid.demo.style

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.apache.fastandroid.R

/**
 * Created by Jerry on 2022/3/10.
 *  CoordinatorLayout 会对所有子view进行偏移,保证他们不会被状态栏挡住
 *  设置
 * https://mp.weixin.qq.com/s/AiCNJAi9CgYDE1UuzCboGg
 */
class FitSystemWindowDemoActivity2:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //设置状态栏为透明
        window.statusBarColor = Color.TRANSPARENT

        setContentView(R.layout.activity_fits_system_window2)
    }
}