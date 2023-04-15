package com.apache.fastandroid.demo.designmode.mediator

/**
 * Created by Jerry on 2023/4/15.
 */
abstract class AbsColleague( val name:String, val mediator:IMediator) {

    abstract fun send(message:String)

    open fun receiver(message:String){
        println("$name received msg:$message")
    }
}