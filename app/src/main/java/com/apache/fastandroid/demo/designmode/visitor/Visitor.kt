package com.apache.fastandroid.demo.designmode.visitor

/**
 * Created by Jerry on 2023/4/15.
 */
interface Visitor {

    fun visit(element: ConcreteElementA)

    fun visit(element: ConcreteElementB)
}