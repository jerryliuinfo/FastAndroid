package com.apache.fastandroid.demo.designmode.template

/**
 * Created by Jerry on 2023/4/15.
 */
class ConcreteClassA: AbstractClass() {
    override fun step3() {
        println("ConcreteClassA step3")
    }

    override fun step2() {
        println("ConcreteClassA step2")
    }

    override fun step1() {
        println("ConcreteClassA step1")
    }
}