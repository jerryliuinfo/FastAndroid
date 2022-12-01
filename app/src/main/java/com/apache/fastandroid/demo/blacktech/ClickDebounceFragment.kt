package com.apache.fastandroid.demo.blacktech

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.apache.fastandroid.databinding.FragmentDebounceClickBinding
import com.tesla.framework.ui.fragment.BaseBindingFragment


/**
 * Created by Jerry on 2022/1/15.
 */
class ClickDebounceFragment : BaseBindingFragment<FragmentDebounceClickBinding>(FragmentDebounceClickBinding::inflate) {


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.btnDebounce.setOnClickListener {
            println("btnDebounce click time:${System.currentTimeMillis()}")
            handleOnClick(it)
        }


    }


    /**
     * 设置5秒内响应一次点击
     */
//    @SingleClick(5000)
//    @DebugLog(priority = Log.ERROR)
//    @Intercept(3)
    fun handleOnClick(v: View?) {
       println ("点击响应！")
    }
}