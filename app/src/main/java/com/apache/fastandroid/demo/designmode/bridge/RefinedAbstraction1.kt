package com.apache.fastandroid.demo.designmode.bridge

/**
 * Created by Jerry on 2023/3/25.
 */
 class RefinedAbstraction1(implementor: Implementor):Abstraction(implementor) {
    override fun operation() {
        println("抽象部分实现1")
        implementor.operation()
    }
}