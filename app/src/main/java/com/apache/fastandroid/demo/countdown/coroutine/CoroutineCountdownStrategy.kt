package com.apache.fastandroid.demo.countdown.coroutine

import com.apache.fastandroid.demo.countdown.CountdownStrategy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex

/**
 *
 * @property progressCallback Function1<Pair<Long, Boolean>, Unit>
 * @property job Job?
 * @property remainingTime Long 剩余时间  是否正在运行
 * @property lock Mutex
 * @constructor
 */
class CoroutineCountdownStrategy(private val progressCallback: (Pair<Long, Boolean>) -> Unit) :
    CountdownStrategy, CoroutineScope by MainScope() {
    private var job: Job? = null
    private var remainingTime: Long = 0 // 存储剩余时间
    private val lock = Mutex() // 用于同步的互斥锁


    override fun start(duration: Long) {
        // 启动倒计时时，初始化剩余时间和协程
        remainingTime = duration

        launchCountdown()
    }

    private fun launchCountdown() {
        if (job?.isActive == true) return // 如果协程已经在运行，则不重复启动
        job?.cancel() // 取消之前的协程（如果有的话）
        job = null
        job = GlobalScope.launch(Dispatchers.Main) {
            var time = remainingTime
            while (time > 0) {
                progressCallback(Pair(time, true)) // 更新进度
                delay(1000)
                time--
                remainingTime = time

            }
            progressCallback(Pair(0, false)) // 倒计时结束
        }
    }

    override fun pause() {
        job?.cancel() // 取消协程
        progressCallback(Pair(remainingTime, false)) // 更新进度状态
    }

    override fun resume() {
        if (job?.isActive == true) return // 如果协程已经在运行，则不重复启动
        launchCountdown() // 重新启动协程
    }

    override fun reset(duration: Long) {
        job?.cancel() // 取消当前协程
        start(duration)
    }

    override fun stop() {
        job?.cancel() // 取消协程
        progressCallback(Pair(0, false)) // 更新进度状态
    }
}