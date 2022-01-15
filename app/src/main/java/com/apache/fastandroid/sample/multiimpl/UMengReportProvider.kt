package com.apache.fastandroid.sample.multiimpl

import com.apache.fastandroid.sample.multiimpl.bean.ReportParams

/**
 * Created by Jerry on 2022/1/15.
 */
class UMengReportProvider(override var enable: Boolean, override var name: String) :IReportProvider() {
    override fun onInit() {
        //UMeng 初始化
    }

    override fun onDetach() {
    }

    override fun onEvent(eventName: String, params: ReportParams) {
        //UMeng 上报
    }
}