package com.apache.fastandroid.demo.designmode.chain

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.apache.fastandroid.demo.designmode.chain.interceptors.CustomInterceptor1
import com.apache.fastandroid.demo.designmode.chain.interceptors.CustomInterceptor2
import com.tesla.framework.ui.fragment.BaseFragment

/**
 * Created by Jerry on 2021/10/19.
 */
class ChainModeDemoFragment:BaseFragment() {
    override fun inflateContentView(): Int {
        return R.layout.fragment_common
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)
        val intercptors = listOf<FInterceptor>(CustomInterceptor1(),CustomInterceptor2())
        var fRequest = FRequest("text", "www.baidu.com")
        val chain = FInterceptorChain(intercptors,0, fRequest)
        chain.procced(fRequest)
    }
}