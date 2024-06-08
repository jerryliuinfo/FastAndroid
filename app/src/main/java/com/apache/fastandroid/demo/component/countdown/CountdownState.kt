package com.apache.fastandroid.demo.component.countdown

/**
 * Created by Jerry on 2024/6/8.
 */
data class CountdownState(
    val remainingTime: Long = 0,
    val isRunning: Boolean = false,
    val isPaused: Boolean = false
)