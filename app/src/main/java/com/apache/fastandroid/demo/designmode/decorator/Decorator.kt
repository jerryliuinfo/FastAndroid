package com.apache.fastandroid.demo.designmode.decorator

/**
 * Created by Jerry on 2023/3/25.
 */
abstract class Decorator(private val sub: Component2):Component2 {
    override fun operation() {
        sub.operation()
    }



}