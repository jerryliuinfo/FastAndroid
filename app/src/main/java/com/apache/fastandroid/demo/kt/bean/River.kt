package com.apache.fastandroid.demo.kt.bean

/**
 * Created by Jerry on 2022/2/7.
 */
class River<T>(var name:String, var length:T) {

    fun getInfo():String{
        var unit = when(length){
            is String -> "米"
            is Number -> "m"
            else -> ""
        }
        return "$name 的长度是 $length $unit"
    }
}