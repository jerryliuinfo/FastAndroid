package com.apache.fastandroid.demo.kt.sealed

/**
 * Created by Jerry on 2022/1/27.
 */
sealed class SealedColor {
    class Red(val value: Int) : SealedColor()
    class Green(val value: Int) : SealedColor()
    class Blue(val name: String) : SealedColor()
    class Gray(var alpha:Int):SealedColor()

    fun isInstance(color: SealedColor) {
        when (color) {
            is SealedColor.Red -> {
                println("red color")
            }
            is SealedColor.Green -> {
                println("green color")
            }
            is SealedColor.Blue -> {
                println("Blue color")

            }
        }
    }
}