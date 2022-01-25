package com.apache.fastandroid.demo.designmode.chain

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentDesignModeChainBinding
import com.apache.fastandroid.demo.designmode.chain.interceptors.CustomInterceptor1
import com.apache.fastandroid.demo.designmode.chain.interceptors.CustomInterceptor3
import com.apache.fastandroid.demo.designmode.chain.interceptors.CustomInterceptor2
import com.tesla.framework.ui.fragment.BaseVMFragment

/**
 * Created by Jerry on 2021/10/19.
 */
class ChainModeDemoFragment:BaseVMFragment<FragmentDesignModeChainBinding>(FragmentDesignModeChainBinding::inflate) {


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)


        viewBinding.btnBasicUsage.setOnClickListener {



            val intercptors = listOf(CustomInterceptor1(), CustomInterceptor2(),CustomInterceptor3())
            var fRequest = FRequest("text", "www.baidu.com")
            val chain = FInterceptorChain(intercptors,0, fRequest)
            chain.procced(fRequest)
        }
    }
}