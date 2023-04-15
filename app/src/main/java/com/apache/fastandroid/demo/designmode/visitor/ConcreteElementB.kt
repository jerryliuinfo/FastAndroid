package com.apache.fastandroid.demo.designmode.visitor

/**
 * Created by Jerry on 2023/4/15.
 */
class ConcreteElementB:Element {
    override fun accept(visitor: Visitor) {
        visitor.visit(this)
    }

    fun operationB(){
        println("ConcreteElementB operationB")
    }

}