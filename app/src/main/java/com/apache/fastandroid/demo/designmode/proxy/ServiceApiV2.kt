package com.apache.fastandroid.demo.designmode.proxy

/**
 * Created by Jerry on 2021/9/21.
 */
class ServiceApiV2(private var serviceApi: ServiceApi):IService {
    fun setServiceApi(serviceApi: ServiceApi) {
        this.serviceApi = serviceApi
    }

    fun doSomething(){
        serviceApi.dosomething()
    }
}