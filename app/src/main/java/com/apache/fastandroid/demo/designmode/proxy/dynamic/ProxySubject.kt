package com.apache.fastandroid.demo.designmode.proxy.dynamic

import com.tesla.framework.component.logger.Logger

/**
 * Created by Jerry on 2023/4/15.
 */
class ProxySubject(private val realSubject: RealSubject):ISubject {
    override fun request() {
        Logger.d("Proxy: start request")
        realSubject.request()
        Logger.d("Proxy: end request")

    }

}