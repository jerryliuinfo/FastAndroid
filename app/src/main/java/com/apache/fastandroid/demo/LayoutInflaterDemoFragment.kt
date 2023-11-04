package com.apache.fastandroid.demo

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentLayoutinflterBinding
import com.tesla.framework.kt.dp
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2022/3/10.
 * 参考:https://mp.weixin.qq.com/s/id8ESD94ixfhxslZWk-cEw
 */
class LayoutInflaterDemoFragment:BaseBindingFragment<FragmentLayoutinflterBinding>(FragmentLayoutinflterBinding::inflate) {
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.btnRootNull.setOnClickListener {
            val button = LayoutInflater.from(context).inflate(R.layout.layout_button,null)
            //第二个参数为null，意味着没有父布局，此时在 layout_button.xml 中设置的宽高是无用的，
            mBinding.rootView.addView(button)
        }

        mBinding.btnRootNull2.setOnClickListener {
            val button = LayoutInflater.from(context).inflate(R.layout.layout_button,null)
            //第二个参数为null，意味着没有父布局，此时在 layout_button.xml 中设置的宽高是无用的，
            val params = mBinding.btnRootNull2.layoutParams as LinearLayout.LayoutParams
            params.width = 600.dp
            params.height = 100.dp
            mBinding.rootView.addView(button,params)
        }

        mBinding.btnRootNotNull.setOnClickListener {
            val button = LayoutInflater.from(context).inflate(R.layout.layout_button,mBinding.rootView,false)
            mBinding.rootView.addView(button)
        }

        mBinding.btnRootNotNull2.setOnClickListener {
            //error java.lang.IllegalStateException: The specified child already has a parent. You must call removeView() on the child's parent first.
            val button = LayoutInflater.from(context).inflate(R.layout.layout_button,mBinding.rootView,true)
            mBinding.rootView.addView(button)
        }


    }
}