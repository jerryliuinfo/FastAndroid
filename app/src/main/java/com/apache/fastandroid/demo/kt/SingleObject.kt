package com.apache.fastandroid.demo.kt

/**
 * Created by Jerry on 2022/1/27.
 */
class SingleObject private constructor(){

    companion object{
        fun get():SingleObject{
            return Holder.instance
        }
    }

    private object Holder{
        val instance = SingleObject()
    }

}