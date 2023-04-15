package com.apache.fastandroid.demo.designmode.mediator

/**
 * Created by Jerry on 2023/4/15.
 */
interface IMediator {

    fun send(message:String, sender: AbsColleague)
}