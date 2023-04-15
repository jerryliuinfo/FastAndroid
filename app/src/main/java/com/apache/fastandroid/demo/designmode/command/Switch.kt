package com.apache.fastandroid.demo.designmode.command

/**
 * Created by Jerry on 2023/4/9.
 */
class Switch(private val turnOnCommand: Command,private val turnOffCommand: Command) {

    fun turnOn(){
        turnOnCommand.execute()
    }

    fun turnOff(){
        turnOffCommand.execute()
    }
}