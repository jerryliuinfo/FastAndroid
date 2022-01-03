package com.apache.fastandroid.util

import java.lang.RuntimeException

/**
 * Created by Jerry on 2022/1/2.
 */
data class AccessDenyException(val msg:String):RuntimeException(msg)
