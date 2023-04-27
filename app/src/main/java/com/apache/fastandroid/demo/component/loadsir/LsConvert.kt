package com.apache.fastandroid.demo.component.loadsir

/**
 * Created by Jerry on 2023/4/16.
 */
interface LsConvert<T> {

    fun map(t:T):Class<out LsCallback>
}