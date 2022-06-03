package com.apache.fastandroid.demo.kt.staticusage

/**
 * Created by Jerry on 2022/6/2.
 */
class Foo {

    companion object{

        val name = "Hello"
        @JvmStatic
        val name2 = "JvmStaticHello"

        @JvmField
        val name3 = "jvmFieldName"


        fun f(){

        }

        @JvmStatic
        fun f2(){

        }
    }
}


fun topLevelFun1(){}

fun topLevelFun2(){}


