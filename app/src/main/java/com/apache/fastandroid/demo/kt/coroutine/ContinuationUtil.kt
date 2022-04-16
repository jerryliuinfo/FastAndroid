package com.apache.fastandroid.demo.kt.coroutine

import kotlinx.coroutines.delay

/**
 * Created by Jerry on 2022/4/10.
 */
object ContinuationUtil {

    suspend fun foo():String{

        return "foo"
    }

    suspend fun bar(a:Int):String{

        return "bar"
    }
}