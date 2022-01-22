package com.apache.fastandroid.demo.designmode.builder

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentBuilderModeBinding
import com.apache.fastandroid.demo.component.loadsir.callback.AnimateCallback
import com.apache.fastandroid.demo.component.loadsir.callback.EmptyCallback
import com.apache.fastandroid.demo.designmode.proxy.ServiceApi
import com.apache.fastandroid.demo.designmode.proxy.ServiceApiV2
import com.apache.fastandroid.demo.designmode.proxy.dynamic.ISubject
import com.apache.fastandroid.demo.designmode.proxy.dynamic.ProxyHandler
import com.apache.fastandroid.demo.designmode.proxy.dynamic.RealSubject
import com.apache.fastandroid.demo.hawk.Hawk
import com.apache.fastandroid.demo.hawk.interfaces.ILogInterceptor
import com.kingja.loadsir.core.LoadSir
import com.tesla.framework.ui.fragment.BaseFragment
import com.tesla.framework.ui.fragment.BaseVBFragment
import kotlinx.android.synthetic.main.fragment_design_mode_proxy.*

/**
 * Created by Jerry on 2021/9/19.
 */
class BuilderModeDemoFragment:BaseVBFragment<FragmentBuilderModeBinding>(FragmentBuilderModeBinding::inflate) {

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