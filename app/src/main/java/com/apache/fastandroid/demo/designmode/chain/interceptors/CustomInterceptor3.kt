package com.apache.fastandroid.demo.designmode.chain.interceptors

import com.apache.fastandroid.demo.designmode.chain.FInterceptor
import com.apache.fastandroid.demo.designmode.chain.FResult
import com.tesla.framework.component.logger.Logger

/**
 * Created by Jerry on 2021/10/19.
 */
class CustomInterceptor3:FInterceptor {
    override fun intercept(chain: FInterceptor.FChain): FResult {
        Logger.d("InterceptorChain CustomInterceptor3 intercept request: %s",chain.request())

        var procced = chain.procced(chain.request())

        return procced
    }


}