package com.apache.fastandroid.demo.designmode.mediator

/**
 * Created by Jerry on 2023/4/15.
 */
class ConcreteMediator:IMediator {

    private var mColleague1:AbsColleague ?= null
    private var mColleague2:AbsColleague ?= null

    fun setColleague1(colleague: AbsColleague){
        this.mColleague1 = colleague
    }

    fun setColleague2(colleague: AbsColleague){
        this.mColleague2 = colleague
    }



    override fun send(message: String, sender: AbsColleague) {
        println("${sender.name} send message: ${message}")
        when(sender){
            mColleague1 -> mColleague2?.receiver(message)
            mColleague2 -> mColleague1?.receiver(message)
        }
    }
}