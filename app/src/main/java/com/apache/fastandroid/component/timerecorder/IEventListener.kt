package com.apache.fastandroid.component.timerecorder

/**
 * Created by Jerry on 2023/3/11.
 */
interface IEventListener {

    //开始进入 AI 诊断
    fun callStart()

    //结束诊断
    fun callEnd()

    fun processStart()

    fun processEnd()

    fun serverStart()

    fun serverEnd()


    fun asrSessionStart()

    fun asrTextFlow()

    fun asrSessionEnd()



    interface Factory {

        fun create(): IEventListener
    }

}