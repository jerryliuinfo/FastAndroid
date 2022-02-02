package com.apache.fastandroid.demo.blacktech

import android.content.pm.ApplicationInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentBlackTechBinding
import com.apache.fastandroid.databinding.FragmentDebounceClickBinding
import com.apache.fastandroid.databinding.FragmentHomeBinding
import com.smartdengg.clickdebounce.DebouncedPredictor
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.ui.fragment.BaseStatusFragmentNew
import com.tesla.framework.ui.fragment.BaseVMFragment
import com.xuexiang.xaop.DebugLog
import com.xuexiang.xaop.Intercept
import com.xuexiang.xaop.SingleClick
import org.lsposed.hiddenapibypass.HiddenApiBypass
import java.lang.reflect.Method
import java.util.Arrays.stream
import java.util.stream.StreamSupport.stream

/**
 * Created by Jerry on 2022/1/15.
 */
class ClickDebounceFragment : BaseVMFragment<FragmentDebounceClickBinding>(FragmentDebounceClickBinding::inflate) {


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)
        DebouncedPredictor.FROZEN_WINDOW_MILLIS = 4000L

        viewBinding.btnDebounce.setOnClickListener {
            println("btnDebounce click time:${System.currentTimeMillis()}")
            handleOnClick(it)
        }


    }


    /**
     * 设置5秒内响应一次点击
     */
    @SingleClick(5000)
    @DebugLog(priority = Log.ERROR)
    @Intercept(3)
    fun handleOnClick(v: View?) {
       println ("点击响应！")
    }
}