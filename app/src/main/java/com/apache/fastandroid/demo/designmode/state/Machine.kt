package com.apache.fastandroid.demo.designmode.state

/**
 * Created by Jerry on 2023/4/15.
 */
object Machine {

    var state:State = OffState()

    fun turnOn(){
        state.turnOn()
    }

    fun turnOff(){
        state.turnOff()
    }
}