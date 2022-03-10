package com.apache.fastandroid.demo

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentLauncherModeBinding
import com.apache.fastandroid.databinding.FragmentLayoutinflterBinding
import com.tesla.framework.kt.dp
import com.tesla.framework.kt.dpInt
import com.tesla.framework.ui.fragment.BaseVBFragment

/**
 * Created by Jerry on 2022/3/10.
 * 参考:https://mp.weixin.qq.com/s/id8ESD94ixfhxslZWk-cEw
 */
class LayoutInflaterDemoFragment:BaseVBFragment<FragmentLayoutinflterBinding>(FragmentLayoutinflterBinding::inflate) {
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        viewBinding.btnRootNull.setOnClickListener {
            val button = LayoutInflater.from(context).inflate(R.layout.layout_button,null)
            //第二个参数为null，意味着没有父布局，此时在 layout_button.xml 中设置的宽高是无用的，
            viewBinding.rootView.addView(button)
        }

        viewBinding.btnRootNull2.setOnClickListener {
            val button = LayoutInflater.from(context).inflate(R.layout.layout_button,null)
            //第二个参数为null，意味着没有父布局，此时在 layout_button.xml 中设置的宽高是无用的，
            val params = viewBinding.btnRootNull2.layoutParams as LinearLayout.LayoutParams
            params.width = 600.dpInt
            params.height = 100.dpInt
            viewBinding.rootView.addView(button,params)
        }

        viewBinding.btnRootNotNull.setOnClickListener {
            val button = LayoutInflater.from(context).inflate(R.layout.layout_button,viewBinding.rootView,false)
            viewBinding.rootView.addView(button)
        }

        viewBinding.btnRootNotNull2.setOnClickListener {
            //error java.lang.IllegalStateException: The specified child already has a parent. You must call removeView() on the child's parent first.
            val button = LayoutInflater.from(context).inflate(R.layout.layout_button,viewBinding.rootView,true)
            viewBinding.rootView.addView(button)
        }


    }
}