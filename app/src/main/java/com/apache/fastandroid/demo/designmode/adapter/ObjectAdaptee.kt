package com.apache.fastandroid.demo.designmode.adapter

/**
 * Created by Jerry on 2023/3/25.
 * 通过组合已有类的方式实现的适配器类
 */
class ObjectAdaptee(private val adaptee: Adaptee):com.apache.fastandroid.demo.designmode.adapter.Target {
    override fun request() {
        adaptee.specialRequest()
    }

}