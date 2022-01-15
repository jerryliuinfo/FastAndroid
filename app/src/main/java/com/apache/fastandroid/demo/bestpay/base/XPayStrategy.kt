package com.apache.fastandroid.demo.bestpay.base

import android.app.Activity
import android.view.View
import com.apache.fastandroid.demo.bestpay.callback.XPayCallback

/**
 * Created by Jerry on 2022/1/15.
 */
interface XPayStrategy<T:XIPayInfo> {

    fun pay(activity:Activity, payInfo:T, callback: XPayCallback)
}