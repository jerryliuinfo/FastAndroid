package com.apache.fastandroid.demo.kt.extensions

import android.content.Context
import android.content.Intent
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import com.apache.fastandroid.jetpack.flow.data.bean.User
import com.apache.fastandroid.state.AquariumPlant
import com.kingja.loadsir.core.LoadSir
import com.skydoves.bundler.Bundler

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

fun loadSirOptions(optionsBuilder: LoadSir.Builder.() -> Unit): LoadSir =
    LoadSir.Builder().apply(optionsBuilder).build()




fun <T,O> List<T>.mapData(transform:(T) -> O):List<O>{
    val destination = mutableListOf<O>()
    for (item in this)
        destination.add(transform(item))
    return destination
}


@JvmSynthetic
inline fun <reified T : Any> Context.xxxOf(
    crossinline block: Bundler.() -> Unit
): Intent {
    return Bundler(Intent(this, T::class.java)).apply(block).intent
}