package com.apache.fastandroid.demo.designmode

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentDesignModeDelegateBinding
import com.apache.fastandroid.demo.designmode.proxy.ServiceApi
import com.apache.fastandroid.demo.designmode.proxy.ServiceApiV2
import com.tesla.framework.ui.fragment.BaseVBFragment
import kotlinx.android.synthetic.main.fragment_design_mode_proxy.*

/**
 * Created by Jerry on 2021/9/19.
 */
class DelegateDemoFragment:BaseVBFragment<FragmentDesignModeDelegateBinding>(FragmentDesignModeDelegateBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        btn_proxy_mode.setOnClickListener {
            val serviceApiV2 = ServiceApiV2(ServiceApi())
            serviceApiV2.doSomething()
        }
    }
}