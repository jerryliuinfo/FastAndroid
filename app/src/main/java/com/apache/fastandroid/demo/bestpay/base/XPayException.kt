package com.apache.fastandroid.demo.bestpay.base

import java.lang.Exception

/**
 * Created by Jerry on 2022/1/15.
 */
internal class XPayException(message: String?, code: Int) : Exception(message) {
    private var code: Int = code

}