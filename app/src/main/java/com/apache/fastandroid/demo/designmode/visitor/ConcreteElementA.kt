package com.apache.fastandroid.demo.designmode.visitor

/**
 * Created by Jerry on 2023/4/15.
 */
class ConcreteElementA:Element {
    override fun accept(visitor: Visitor) {
        visitor.visit(this)
    }

    fun operationA(){
        println("ConcreteElementA operationA")
    }

}