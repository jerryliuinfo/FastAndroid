package com.apache.fastandroid.demo

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentToolsBinding
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2022/5/22.
 */
class ToolsFragment:BaseBindingFragment<FragmentToolsBinding>(FragmentToolsBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.btnAdb.setOnClickListener {
            adbUsage()
        }
        mBinding.btnPlugins.setOnClickListener {
            pluginsUsage()
        }

    }

    private fun pluginsUsage() {
        val url = "https://github.com/getActivity/StudioPlugins"
    }

    private fun adbUsage() {
        val url = "https://github.com/mzlogin/awesome-adb"
    }
}