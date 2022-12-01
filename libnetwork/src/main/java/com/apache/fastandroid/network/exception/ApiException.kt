package com.apache.fastandroid.network.exception

import java.lang.RuntimeException

/**
 * Created by Jerry on 2022/11/30.
 */
class ApiException(private val code:Int ?= 0,private val msg:String):RuntimeException(msg) {
}