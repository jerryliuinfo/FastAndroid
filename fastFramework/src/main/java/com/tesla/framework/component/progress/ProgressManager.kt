package com.tesla.framework.component.progress

import com.tesla.framework.component.logger.Logger
import kotlin.math.abs


class ProgressManager(private val config: ProgressConfig,private val calculator: ICalculator) : Runnable {


    //当前展示的进度条进度
    private var mCountProgress: Int = 0
    private val DEFAULT_INTERVAL = config.maxThresold / 100

    // 当进度条进度小于实际进度时，多久之内快速到达实际进度
    private val ACCELERATE_INTERVAL = config.accelerateTime

    private var mListener: IProgressListener? = null

    private var mCanceled = false

    private var startTime: Long = 0


    override fun run() {
        startTime = System.currentTimeMillis()
        resetData()
        while (mCountProgress < 100 && !mCanceled) {
            mCountProgress++
            val mRealProgress = calculator.getProgress()
            //真是进度已经走完了
            if (mRealProgress >= 100) {
                //差的百分比
                val restProgress = 100 - mCountProgress
                //当前已经花了多长时间
                val costTime = System.currentTimeMillis() - startTime
                Logger.d("mCompleted111 mCountProgress:$mCountProgress,mRealProgress:${mRealProgress},restProgress:$restProgress, costTime:$costTime")

                if (restProgress <= 0) {
                    continue
                }
                //如果花的时间小于最小时间，则按最小时间计算当前应该睡眠的时间，否则，快速加速到百分之百吧
                val sleepInterval =
                    if (costTime < config.minThresold) (config.minThresold - costTime) / restProgress else ACCELERATE_INTERVAL / restProgress

                Logger.d("mCompleted222 mCountProgress:$mCountProgress,mRealProgress:${mRealProgress},restProgress:$restProgress, costTime:$costTime,sleepInterval:$sleepInterval")
                Thread.sleep(sleepInterval.toLong())

            } else {
                val progressDiff = mCountProgress - mRealProgress
                //如果展示进度小于实际任务进度,则将展示进度加快(300 ms内加速赶上); 否则按照计算的间隔休眠
                val sleepInterval =
                    if (progressDiff < 0) (ACCELERATE_INTERVAL / abs(progressDiff)) else DEFAULT_INTERVAL
                Logger.d("not completed mCountProgress:$mCountProgress,mRealProgress:$mRealProgress, progressDiff:$progressDiff,sleepInterval:$sleepInterval")
                Thread.sleep(sleepInterval.toLong())
            }
            mListener?.onProgress(mCountProgress)
        }
        onEnd()

        Logger.d("total cost time:${System.currentTimeMillis() - startTime} ms")

    }

    fun start() {
        calculator.clearTask()
        mListener?.onStart()

        this.run()
    }

    fun setProgressListener(listener: IProgressListener) {
        this.mListener = listener
    }

    private fun onEnd() {
        mCountProgress = 0
        mListener?.onFinish()
    }

    fun costTime(): Long {
        return System.currentTimeMillis() - startTime
    }

    private fun resetData() {
        mCountProgress = 0
        mCanceled = false
    }

    fun stop() {
        mCanceled = true
        resetData()

    }


}