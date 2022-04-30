package com.apache.fastandroid.demo.kt.sealed

/**
 * Created by Jerry on 2022/4/26.
 */
sealed class Mammal(val name:String)

//所有密闭类的子类必须在同一个包下
class Cat(val catName:String): Mammal(catName)

class Human(val humanName:String, val job:String): Mammal(humanName)

fun greetMammal(mammal: Mammal):String{
    return when(mammal){
        is Human -> "Hello ${mammal.name}; you are working as a ${mammal.job}"
        is Cat -> "Hello ${mammal.name}"
    }
}


