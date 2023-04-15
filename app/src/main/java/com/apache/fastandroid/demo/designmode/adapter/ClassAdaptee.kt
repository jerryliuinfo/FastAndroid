package com.apache.fastandroid.demo.designmode.adapter

/**
 * Created by Jerry on 2023/3/25.
 * 通过继承已有类的方式实现的适配器类
 */
class ClassAdaptee:Adaptee(),Target {
    override fun request() {
        specialRequest()
    }
}