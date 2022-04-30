package com.apache.fastandroid.demo.bean

/**
 * Created by Jerry on 2021/12/15.
 */
data class Person(var name:String, var age:Int):People(){
    override fun equals(other: Any?): Boolean {
        return other is Person && other.name == name
    }
}
