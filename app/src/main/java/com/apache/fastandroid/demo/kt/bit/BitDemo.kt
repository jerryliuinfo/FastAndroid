package com.apache.fastandroid.demo.kt.bit

/**
 * Created by Jerry on 2022/4/30.
 */
class BitDemo {
    // 相当于 1 << 0
    val FIRST = 1 shl 0  // 0000 0001
    val STATUS_BARS = FIRST  // 0000 0001
    val NAVIGATION_BARS = 1 shl 1  // 0000 0010
    val CAPTION_BAR = 1 shl 2 // 0000 0100

    var type:Int = FIRST

    private val UNSET = -1
    private val SIZE_MULTIPLIER = 1 shl 1
    private val DISK_CACHE_STRATEGY = 1 shl 2
    private val PRIORITY = 1 shl 3
    private val ERROR_PLACEHOLDER = 1 shl 4
    private val ERROR_ID = 1 shl 5
    private val PLACEHOLDER = 1 shl 6
    private val PLACEHOLDER_ID = 1 shl 7
    private val IS_CACHEABLE = 1 shl 8
    private val OVERRIDE = 1 shl 9
    private val SIGNATURE = 1 shl 10
    private val TRANSFORMATION = 1 shl 11
    private val RESOURCE_CLASS = 1 shl 12

}