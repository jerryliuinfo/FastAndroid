package com.apache.fastandroid.demo.countdown

/**
 * Created by Jerry on 2024/6/8.
 */
interface CountdownStrategy {
    fun start(duration: Long)
    fun pause()
    fun resume()
    fun reset(duration: Long)
    fun stop()
}