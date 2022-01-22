package com.apache.fastandroid.demo.bestpay.wxpay

import com.tesla.framework.component.logger.Logger

/**
 * Created by Jerry on 2022/1/15.
 */
class WxApi {
    fun init(appId:String){

    }

    fun sendRequest(payInfo: WxPayInfo){
        Logger.d("sendRequest")

    }

    fun handleIntent(){
        Logger.d("haneleIntent")
    }
}