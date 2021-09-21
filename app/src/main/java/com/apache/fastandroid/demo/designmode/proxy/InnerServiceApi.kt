package com.apache.fastandroid.demo.designmode.proxy

import com.tesla.framework.common.util.log.NLog

/**
 * Created by Jerry on 2021/9/21.
 */
class InnerServiceApi() {
    companion object{
        private const val TAG = "InnerServiceApi"
    }
    fun doSomething(){
        NLog.d(TAG, "InnerServiceApi doSomething")
    }
}