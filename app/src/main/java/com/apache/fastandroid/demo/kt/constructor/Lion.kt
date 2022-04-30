package com.apache.fastandroid.demo.kt.constructor

/**
 * Created by Jerry on 2022/4/26.
 */
open class Lion(val name:String, val origin:String) {

    fun sayHello(){
        println("$name, the lion from $origin says: graoh")
    }
}

class Asiatic(name:String):Lion(name = name, origin = "India")