package com.apache.fastandroid.demo.kt.constructor

/**
 * Created by Jerry on 2022/4/26.
 */
open class Lion(val name:String, val origin:String) {
    val firstProperty = "First property: $name".also(::println)
    init {
        println("first property block that println :$name")
    }

    val secondProperty = "First property: $name".also(::println)
    init {
        println("secondProperty property block that println :$origin")
    }

    val customerName = name.uppercase()

    fun sayHello(){
        println("$name, the lion from $origin says: graoh")
    }
}

class Asiatic(name:String):Lion(name = name, origin = "India")