package com.apache.fastandroid.demo.designmode

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.apache.fastandroid.demo.designmode.proxy.ServiceApi
import com.apache.fastandroid.demo.designmode.proxy.ServiceApiV2
import com.tesla.framework.ui.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_design_mode_proxy.*

/**
 * Created by Jerry on 2021/9/19.
 */
class ProxyDemoFragment:BaseFragment() {
    override fun inflateContentView(): Int {
        return R.layout.fragment_design_mode_proxy
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        btn_proxy_mode.setOnClickListener {
            val serviceApiV2 = ServiceApiV2(ServiceApi())
            serviceApiV2.doSomething()
        }
    }
}