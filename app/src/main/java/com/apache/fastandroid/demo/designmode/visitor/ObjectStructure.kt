package com.apache.fastandroid.demo.designmode.visitor

/**
 * Created by Jerry on 2023/4/15.
 */
class ObjectStructure {

    private val mElements = mutableListOf<Element>()

    init {
        mElements.add(ConcreteElementA())
        mElements.add(ConcreteElementB())
    }

    fun accept(visitor: Visitor){
        for (element in mElements){
            element.accept(visitor)
        }
    }
}