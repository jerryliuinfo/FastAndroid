package com.apache.fastandroid.demo.bestpay.wxpay

import android.text.TextUtils
import com.apache.fastandroid.demo.bestpay.base.XIPayInfo

/**
 * Created by Jerry on 2022/1/15.
 */
data class WxPayInfo(val appId:String,val sign:String, val timeStamp:String, val partnerId:String):XIPayInfo {
    override fun isValid(): Boolean {
        return !TextUtils.isEmpty(sign)
    }


}