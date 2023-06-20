package com.apache.fastandroid.demo.designmode.bridge

/**
 * Created by Jerry on 2023/3/25.
 */
abstract class Abstraction( val implementor: Implementor) {

    abstract fun operation()
}