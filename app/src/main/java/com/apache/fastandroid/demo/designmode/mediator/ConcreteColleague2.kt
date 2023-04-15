package com.apache.fastandroid.demo.designmode.mediator

/**
 * Created by Jerry on 2023/4/15.
 */
class ConcreteColleague2(name:String, mediator:IMediator):AbsColleague(name,mediator) {
    override fun send(message: String) {
        mediator.send(message,this)
    }
}