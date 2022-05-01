package com.apache.fastandroid.demo.kt.delegate

/**
 * Created by Jerry on 2022/5/1.
 */
class ScreamBehavior(val n:String):SoundBehavior {
    override fun makeSound() {
        println("ScreamBehavior :${n.toUpperCase()}")
    }
}