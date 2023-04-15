package com.apache.fastandroid.demo.designmode.observer

import com.tesla.framework.component.logger.Logger

/**
 * Created by Jerry on 2023/3/12.
 */
class ConcreteObserver(val name:String):Observer {
    override fun onChanged(subject: Subject) {
        Logger.d("观察者:${name} 收到了通知:${subject.javaClass.simpleName} 发生了变化")
    }
}