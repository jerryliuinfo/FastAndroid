package com.apache.fastandroid.demo.designmode.template

/**
 * Created by Jerry on 2023/4/15.
 */
abstract class AbstractClass {

    fun doSomething(){
        step1()
        step2()
        step3()
    }

    abstract fun step3()

    abstract fun step2()

    abstract fun step1()
}