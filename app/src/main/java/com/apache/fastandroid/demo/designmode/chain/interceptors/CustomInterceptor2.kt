package com.apache.fastandroid.demo.designmode.chain.interceptors

import android.view.View
import com.apache.fastandroid.demo.designmode.chain.FInterceptor
import com.apache.fastandroid.demo.designmode.chain.FResult
import com.tesla.framework.common.util.log.NLog

/**
 * Created by Jerry on 2021/10/19.
 */
class CustomInterceptor2:FInterceptor {
    override fun intercept(chain: FInterceptor.FChain): FResult {
        NLog.d("InterceptorChain", "CustomInterceptor1 intercept request: %s",chain.request())

        var procced = chain.procced(chain.request())

        return procced
    }


}