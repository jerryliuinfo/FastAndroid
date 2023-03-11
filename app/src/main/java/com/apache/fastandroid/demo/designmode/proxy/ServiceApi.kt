package com.apache.fastandroid.demo.designmode.proxy


/**
 * Created by Jerry on 2021/9/21.
 */
class ServiceApi:IService {
    private val innerServiceApi: InnerServiceApi = InnerServiceApi()

    fun dosomething(){
        innerServiceApi.doSomething()
    }

}