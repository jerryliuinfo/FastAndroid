package com.apache.fastandroid.demo.kt.lambda

/**
 * Created by Jerry on 2022/7/15.
 */
interface Transform<T,R> {
    fun transform(t:T):R
}