package com.apache.fastandroid.sample.multiimpl

import android.util.Log
import com.apache.fastandroid.sample.multiimpl.bean.ReportParams
import com.orhanobut.logger.Logger

/**
 * Created by Jerry on 2022/1/15.
 */
class EasyReport {
    companion object{

        var debug:Boolean = true
        private var providers:MutableList<IReportProvider> = ArrayList()

        fun registerProvider(reportProvider: IReportProvider){
            providers.add(reportProvider.apply {
                onInit()
            })
        }

        fun unRegisterProvider(reportProvider: IReportProvider){
            providers.remove(reportProvider.apply {
                onDetach()
            })
        }

        fun dispatchEvent(event:String, params: ReportParams){
            for ( provider in providers){
                provider.onEvent(event,params)
            }
        }
    }


    fun doTrackEvent(eventName: String, params: ReportParams){
        if (providers.isEmpty()){
            Logger.d( "Try track event $eventName, but the providers is Empty.")
            return
        }

        val logMessage = if (debug){
            StringBuilder().apply {
                append(" ")
                append("\n onEvent:${eventName}")
                for ((key, value) in params.data) {
                    append("\n$key = $value")
                }
            }
        }else{
            null
        }

        for (provider:IReportProvider in providers){
            if (!provider.enable){
                logMessage?.append("\n try report:$eventName but provider is disabled")
                continue
            }
            logMessage?.append("\nTry track event $eventName with provider ${provider.name}.")
            provider.onEvent(eventName,params)
        }
        logMessage?.append("\n------------------------------------------------------")?.also {
            Logger.d(it.toString())
        }
    }
}
