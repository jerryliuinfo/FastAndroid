package com.apache.fastandroid.demo.kt.staticusage

/**
 * Created by Jerry on 2022/6/2.
 */
object ObjectClass {

    val name = "Hello"
    @JvmStatic
    val name2 = "jvmStaticName"
    @JvmField
    val name3 = "jvmFieldName"


    fun f(){

    }

    @JvmStatic
    fun f2(){

    }
}

fun String.test1(){
    println("SingleObject test1")
}