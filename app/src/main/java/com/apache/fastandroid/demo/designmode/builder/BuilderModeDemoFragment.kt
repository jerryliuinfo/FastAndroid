package com.apache.fastandroid.demo.designmode.builder

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentBuilderModeBinding
import com.apache.fastandroid.demo.component.loadsir.callback.AnimateCallback
import com.apache.fastandroid.demo.component.loadsir.callback.EmptyCallback
import com.apache.fastandroid.demo.hawk.Hawk
import com.apache.fastandroid.demo.hawk.interfaces.ILogInterceptor
import com.kingja.loadsir.core.LoadSir
import com.tesla.framework.ui.fragment.BaseVMFragment

/**
 * Created by Jerry on 2021/9/19.
 */
class BuilderModeDemoFragment:BaseVMFragment<FragmentBuilderModeBinding>(FragmentBuilderModeBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        viewBinding.btnBuilderGlobal.setOnClickListener {
            Hawk.init(requireContext()).apply {
                logInterceptor = object :ILogInterceptor{
                    override fun onLog(message: String) {
                    }

                }
            }.build()

            viewBinding.btnBuilerSingle.setOnClickListener {
                val loadSir = LoadSir.Builder()
                    .addCallback(EmptyCallback())
                    .addCallback(AnimateCallback())
                    .setDefaultCallback(AnimateCallback::class.java)
                    .build()
            }
        }

    }
}