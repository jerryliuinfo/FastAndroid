package com.apache.fastandroid.demo.kt.singleton

/**
 * Created by Jerry on 2022/1/27.
 */
class SingleObject private constructor(){

    companion object{
        fun getInstance(): SingleObject {
            return Holder.instance
        }
    }

    private object Holder{
        val instance = SingleObject()
    }

}


fun String.test1(){
    println("SingleObject test1")
}