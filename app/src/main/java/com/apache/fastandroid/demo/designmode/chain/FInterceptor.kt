package com.apache.fastandroid.demo.designmode.chain

/**
 * Created by Jerry on 2021/10/19.
 */
interface FInterceptor {

    fun intercept(fChain: FChain):FResult

    interface FChain{
        fun request():FRequest
        fun procced(request:FRequest):FResult
    }
}