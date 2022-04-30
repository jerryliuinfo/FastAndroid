package com.apache.fastandroid.demo.kt.enum

/**
 * Created by Jerry on 2022/4/26.
 */
enum class Colour(val argb:Int) {
    RED(0XFF0000),
    GREEN(0X00FF00),
    BLUE(0X0000FF),
    YELLOW(0XFFFF00);

    fun containsRed() = this.argb and 0xFF0000 != 0
}