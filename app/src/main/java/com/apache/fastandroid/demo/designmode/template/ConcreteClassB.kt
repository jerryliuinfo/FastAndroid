package com.apache.fastandroid.demo.designmode.template

/**
 * Created by Jerry on 2023/4/15.
 */
class ConcreteClassB: AbstractClass() {
    override fun step3() {
        println("ConcreteClassB step3")
    }

    override fun step2() {
        println("ConcreteClassB step2")
    }

    override fun step1() {
        println("ConcreteClassB step1")
    }
}