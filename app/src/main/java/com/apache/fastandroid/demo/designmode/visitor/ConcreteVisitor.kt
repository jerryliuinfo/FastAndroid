package com.apache.fastandroid.demo.designmode.visitor

/**
 * Created by Jerry on 2023/4/15.
 */
class ConcreteVisitor:Visitor {
    override fun visit(element: ConcreteElementA) {
        println("ConcreteVisitor.visit(ConcreteElementA)")
        element.operationA()
    }

    override fun visit(element: ConcreteElementB) {
        println("ConcreteVisitor.visit(ConcreteElementB)")
        element.operationB()
    }
}