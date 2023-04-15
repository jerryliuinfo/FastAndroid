package com.apache.fastandroid.demo.designmode.state

/**
 * Created by Jerry on 2023/4/15.
 */
class OnState:State {
    override fun turnOn() {
        println("the machine is already on")
    }

    override fun turnOff() {
        println("turn off the machine")
    }
}