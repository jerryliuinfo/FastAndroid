package com.apache.fastandroid.demo.designmode.chain.interceptors

import com.apache.fastandroid.demo.designmode.chain.FInterceptor
import com.apache.fastandroid.demo.designmode.chain.FResult
import com.tesla.framework.component.logger.Logger

/**
 * Created by Jerry on 2021/10/19.
 */
class CustomInterceptor1:FInterceptor {
    override fun intercept(chain: FInterceptor.FChain): FResult {
        var request = chain.request()
        Logger.d("InterceptorChain CustomInterceptor1 intercept request: %s",request)

        var url = inflate(request.name, request.url)
        if (url != null){
            return FResult(request.name,url)
        }else{
            return chain.procced(request)
        }
    }

    private fun inflate( name:String, url:String):String?{
       /* if ("text" == name){
            return "textUrl"
        }*/
        return null
    }
}