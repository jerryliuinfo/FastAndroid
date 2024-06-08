package com.apache.fastandroid.demo.countdown.timer

import com.apache.fastandroid.demo.countdown.CountdownStrategy
import java.util.Timer
import java.util.TimerTask

/**
 * Created by Jerry on 2024/6/8.
 */
class TimerCountdownStrategy(private val progressCallback: (Pair<Long, Boolean>) -> Unit) : CountdownStrategy {
    private var timer: Timer? = null

    override fun start(duration: Long) {
        timer = Timer()
        var time = duration
        timer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                time --
                // 更新倒计时进度
                if (time > 0) {
                    // 更新倒计时UI或逻辑
                    progressCallback(Pair(time, true))
                } else {
                    // 当倒计时完成时，停止Timer并更新进度
                    timer?.cancel()
                    progressCallback(Pair(0, false))
                }
            }
        }, 0, 1000)
    }

    override fun pause() {
        timer?.cancel()
        progressCallback(Pair(0, false))
    }

    override fun resume() {
        // 重新启动Timer
    }

    override fun reset(duration: Long) {
        // 重置Timer
    }

    override fun stop() {
        timer?.cancel()
    }
}
