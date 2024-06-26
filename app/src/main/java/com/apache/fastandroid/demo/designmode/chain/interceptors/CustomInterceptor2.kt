package com.apache.fastandroid.demo.designmode.chain.interceptors

import com.apache.fastandroid.demo.designmode.chain.FInterceptor
import com.apache.fastandroid.demo.designmode.chain.FResult
import com.tesla.framework.component.logger.Logger

/**
 * Created by Jerry on 2021/10/19.
 */
class CustomInterceptor2:FInterceptor {
    override fun intercept(chain: FInterceptor.FChain): FResult {
        Logger.d("InterceptorChain CustomInterceptor2 intercept request: %s",chain.request())

        var request = chain.request()
        if (request.url == "www.baidu.com"){
            return FResult(url = request.url, name = request.name, interrupt = true)
        }
        var procced = chain.procced(chain.request())

        return procced
    }


}