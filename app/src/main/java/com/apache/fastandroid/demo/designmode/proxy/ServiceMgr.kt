package com.apache.fastandroid.demo.designmode.proxy

/**
 * Created by Jerry on 2021/9/21.
 */
class ServiceMgr private constructor() {
    private val serviceApi: ServiceApi = ServiceApi()
    private val serviceApiV2: ServiceApiV2 = ServiceApiV2(serviceApi)
    private val wechatApi: WechatApi = WechatApi()

    fun <T : IService?> getApi(tClass: Class<T>): T {
        var ret: IService = serviceApi
        if (tClass.isAssignableFrom(WechatApi::class.java)) {
            ret = wechatApi
        } else if (tClass.isAssignableFrom(ServiceApiV2::class.java)) {
            ret = serviceApiV2
        } else if (tClass.isAssignableFrom(ServiceApi::class.java)) {
            ret = serviceApi
        }
        return ret as T
    }

    companion object {
        val instance = ServiceMgr()
    }
}