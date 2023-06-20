package com.apache.fastandroid.demo.designmode.decorator

import com.apache.fastandroid.demo.designmode.composite.Component


/**
 * Created by Jerry on 2023/3/25.
 */
class ConcreteDecoratorA(component: Component2):Decorator(component) {

    override fun operation() {
        super.operation()
        println("I'm a concrete decorator A: operation")


    }
}