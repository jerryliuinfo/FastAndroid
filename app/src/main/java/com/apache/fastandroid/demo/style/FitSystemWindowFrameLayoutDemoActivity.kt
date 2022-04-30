package com.apache.fastandroid.demo.style

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.OnApplyWindowInsetsListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.ActivityFramelayoutFitsSystemWindowBinding
import kotlinx.android.synthetic.main.constraint_group.*

/**
 * Created by Jerry on 2022/3/10.
 * <!--FrameLayout 中设置 android:fitsSystemWindows="true"
    是无用的，因为FrameLayout 内部并没有对这个属性做处理，可以仿照
    Coordinatelayout 的方式来处理
 * https://mp.weixin.qq.com/s/AiCNJAi9CgYDE1UuzCboGg
 */
class FitSystemWindowFrameLayoutDemoActivity:AppCompatActivity() {

    private lateinit var binding: ActivityFramelayoutFitsSystemWindowBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //设置状态栏为透明
        window.statusBarColor = Color.TRANSPARENT

//        setContentView(R.layout.activity_framelayout_fits_system_window)
        binding = ActivityFramelayoutFitsSystemWindowBinding.inflate(layoutInflater)
        setContentView(binding.rootView)
        val frameLayout = findViewById<FrameLayout>(R.id.rootView)
        frameLayout.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)

        /**
         * 现在我们仍然实现了沉浸式状态栏的效果，但问题是FrameLayout中的按钮也延伸到状态栏区域了，
         * 这就是前面所说的可交互控件被状态栏遮挡的问题。
         * CoordinatorLayout嘛，它已经帮我们考虑好到这些事情，自动会将内部的控件进行偏移。
         * 而现在FrameLayout显然是不会帮我们做这些事情的，所以我们得想办法自己解决
         * 这里其实可以借助setOnApplyWindowInsetsListener()函数去监听WindowInsets发生变化的事件，
         * 当有监听到发生变化时，我们可以读取顶部Insets的大小，然后对控件进行相应距离的偏移。
         */

        ViewCompat.setOnApplyWindowInsetsListener(binding.button
        ) { v, insets ->
            insets?.apply {
                println("WindowInsetsListener start:$systemWindowInsetLeft, top:$systemWindowInsetTop, right:$systemWindowInsetRight,bottom:$systemWindowInsetBottom")
                val params = v.layoutParams as FrameLayout.LayoutParams
                params.topMargin = systemWindowInsetTop
            }
            insets
        }
    }
}