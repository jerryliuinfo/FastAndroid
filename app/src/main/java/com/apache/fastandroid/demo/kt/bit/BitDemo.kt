package com.apache.fastandroid.demo.kt.bit

/**
 * Created by Jerry on 2022/4/30.
 */
class BitDemo {
    //相当于 1 << 0
    val FIRST = 1 shl 0  //0000 0001
    val STATUS_BARS = FIRST  //0000 0001
    val NAVIGATION_BARS = 1 shl 1  //0000 0010
    val CAPTION_BAR = 1 shl 2 //0000 0100

    var type:Int = FIRST

}