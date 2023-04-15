package com.apache.fastandroid.demo.designmode.bridge

/**
 * Created by Jerry on 2023/3/25.
 * 实现桥接接口
 */
class ConcreteImplementor1:Implementor {
    override fun operation() {
        println("具体实现方法1")
    }
}