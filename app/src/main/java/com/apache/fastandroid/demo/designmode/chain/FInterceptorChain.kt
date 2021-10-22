package com.apache.fastandroid.demo.designmode.chain

import com.tesla.framework.common.util.log.NLog

/**
 * Created by Jerry on 2021/10/19.
 */
open class FInterceptorChain(private val interceptors:List<FInterceptor>, private val index:Int, private val request:FRequest):
    FInterceptor.FChain {

    companion object{
         const val TAG = "InterceptorChain"
    }
    override fun request(): FRequest {
        return request
    }

    override fun procced(request: FRequest): FResult {
        NLog.d(TAG, "InterceptorChain procced index: %s, request: %s",index,request)
        if (index == interceptors.size){
            return FResult(request.name,request.url)
        }

        val next = FInterceptorChain(interceptors,index+1,request)
        val interceptor = interceptors[index]
        var nextIntercept = interceptor.intercept(next)
        return nextIntercept

    }


}