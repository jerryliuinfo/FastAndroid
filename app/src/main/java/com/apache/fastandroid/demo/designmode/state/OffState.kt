package com.apache.fastandroid.demo.designmode.state

/**
 * Created by Jerry on 2023/4/15.
 */
class OffState:State {
    override fun turnOn() {
        println("turn on the machine")
    }

    override fun turnOff() {
        println("the machine is already off")

    }
}