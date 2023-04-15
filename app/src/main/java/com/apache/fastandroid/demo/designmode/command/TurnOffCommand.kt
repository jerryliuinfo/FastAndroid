package com.apache.fastandroid.demo.designmode.command

/**
 * Created by Jerry on 2023/4/9.
 */
class TurnOffCommand(private val light:Light):Command {
    override fun execute() {
        light.turnOff()
    }
}