package com.apache.fastandroid.demo.countdown

import androidx.lifecycle.LifecycleObserver

/**
 * Created by Jerry on 2024/6/8.
 */
class CountdownComponent(private val strategy: CountdownStrategy): LifecycleObserver {

    fun start(duration: Long):CountdownComponent {
        strategy.start(duration)
        return this
    }

    fun pause() {
        strategy.pause()
    }

    fun resume() {
        strategy.resume()
    }

    fun reset(duration: Long) {
        strategy.reset(duration)
    }

    fun stop() {
        strategy.stop()
    }


}
