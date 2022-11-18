package com.apache.fastandroid.demo.bestpay.wxpay

import android.app.Activity
import com.apache.fastandroid.demo.bestpay.base.XPayStrategy
import com.apache.fastandroid.demo.bestpay.callback.XPayCallback

/**
 * Created by Jerry on 2022/1/15.
 */
class WxPay:XPayStrategy<WxPayInfo> {

    private var payInfo: WxPayInfo? =null
    private var callback:XPayCallback ?= null

    private var initializated = false

    private  var wxApi:WxApi ? = null

    fun getWxAPi():WxApi?{
        if (wxApi == null){
            initWxApi()
        }
        return wxApi
    }


    companion object{
        @Volatile
        private lateinit var sInstance:WxPay
        fun getInstance():WxPay{
            if (!::sInstance.isInitialized){
                synchronized(WxPay::class.java){
                    if (!::sInstance.isInitialized){
                        sInstance = WxPay()
                    }
                }
            }
            return sInstance
        }
    }

    override fun pay(activity: Activity, payInfo: WxPayInfo, callback: XPayCallback) {
        this.payInfo = payInfo
        this.callback = callback;
        if (payInfo == null || !payInfo.isValid() ){

            if (callback != null){
                callback.failed(100, "参数不合法")
            }
            return
        }
        if (!initializated){
            initWxApi()
        }

        wxApi?.sendRequest(payInfo)
    }

    private fun initWxApi() {
        wxApi = WxApi().apply {
            init(payInfo?.partnerId ?: "")
            initializated = true
        }

    }

    fun onResp(code:Int, message:String){
        if (callback == null){
            return
        }
        when(code){
            101 -> callback?.success()
            102 -> callback?.failed(code,message)
            else -> callback?.cancel()
        }

    }

}