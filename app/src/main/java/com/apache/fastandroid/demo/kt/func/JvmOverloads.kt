package com.apache.fastandroid.demo.kt.func

import kotlin.jvm.JvmOverloads

/**
 * Created by Jerry on 2022/11/5.
 */
object JvmOverloads {


    @JvmStatic
    fun notAnnotateWithJvmOverloads(name:String, age:Int = 10){

    }

    @JvmOverloads
    @JvmStatic
    fun annotateWithJvmOverloads(name:String, age:Int = 10){

    }

    @JvmStatic
    fun main(args: Array<String>) {
        notAnnotateWithJvmOverloads("")
        annotateWithJvmOverloads("",10)
    }

}