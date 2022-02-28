package com.apache.fastandroid.demo.kt.extensions

import com.apache.fastandroid.state.AquariumPlant

/**
 * Created by Jerry on 2022/2/27.
 */
fun AquariumPlant.isRed() = color == "red"
//fun AquariumPlant.isBig() = size > 50 //error

fun AquariumPlant.print() = println("print AquariumPlant")
fun GreenLeafyPlant.print() = println("print GreenLeafyPlant")

val AquariumPlant.isGrren:Boolean
    get() = color == "green"

fun AquariumPlant?.pull() {
    this?.apply {
        println("removing $this")
    }
}

