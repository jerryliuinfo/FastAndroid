package com.apache.fastandroid.demo.jni

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentJniDemoBinding
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2022/6/5.
 */
class JniJavaCallCDemoFragment:BaseBindingFragment<FragmentJniDemoBinding>(FragmentJniDemoBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.btnJavaCallC.setOnClickListener {
            javaCallC()
        }
    }

    private fun javaCallC() {
        mBinding.tvResult.text = stringFromJNI()
    }

    external fun stringFromJNI():String?


    companion object{
        init {
            System.loadLibrary("hello-jni")
        }
    }
}