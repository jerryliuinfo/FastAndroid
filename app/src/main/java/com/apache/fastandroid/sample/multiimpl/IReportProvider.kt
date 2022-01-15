package com.apache.fastandroid.sample.multiimpl

import com.apache.fastandroid.sample.multiimpl.bean.ReportParams

/**
 * Created by Jerry on 2022/1/15.
 */
abstract class IReportProvider {
    
    abstract var enable:Boolean
    
    abstract var name:String

    abstract fun onInit()

    abstract fun onDetach()

    abstract fun onEvent(eventName:String, params: ReportParams)
    
    
}