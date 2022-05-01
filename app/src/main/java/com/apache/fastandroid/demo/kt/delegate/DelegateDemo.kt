package com.apache.fastandroid.demo.kt.delegate

/**
 * Created by Jerry on 2022/5/1.
 */
class DelegateDemo {
    var p:String by DelegateProperty()

    override fun toString(): String {
        return "DelegateDemo---"
    }
}