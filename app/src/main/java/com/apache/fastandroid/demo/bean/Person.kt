package com.apache.fastandroid.demo.bean

/**
 * Created by Jerry on 2021/12/15.
 */
data class Person(var name:String ?= null, var age:Int = 10):People(){
    override fun equals(other: Any?): Boolean {
        return other is Person && other.name == name
    }
}
