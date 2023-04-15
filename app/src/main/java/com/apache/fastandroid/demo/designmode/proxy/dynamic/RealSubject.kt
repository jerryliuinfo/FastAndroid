package com.apache.fastandroid.demo.designmode.proxy.dynamic

import com.tesla.framework.component.logger.Logger

/**
 * Created by Jerry on 2019/2/2.
 */
class RealSubject : ISubject {
    override fun request() {
        Logger.d("RealSubject:Handle request")
    }
}