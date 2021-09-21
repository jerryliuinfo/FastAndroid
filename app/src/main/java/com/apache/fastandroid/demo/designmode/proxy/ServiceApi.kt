package com.apache.fastandroid.demo.designmode.proxy

import com.apache.fastandroid.demo.designmode.proxy.InnerServiceApi
import com.tesla.framework.common.util.log.NLog

/**
 * Created by Jerry on 2021/9/21.
 */
class ServiceApi:IService {
    private val innerServiceApi: InnerServiceApi = InnerServiceApi()

    fun dosomething(){
        innerServiceApi.doSomething()
    }

}