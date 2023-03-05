package com.apache.fastandroid.demo.progress

import androidx.core.math.MathUtils
import com.tesla.framework.component.logger.Logger
import kotlin.concurrent.thread
import kotlin.math.abs


class ProgressManager(private val config: ProgressConfig) : Runnable {


    //当前展示的进度条进度
    private var mCountProgress: Int = 0
    private val DEFAULT_INTERVAL = config.maxThresold / 100
    // 当进度条进度小于实际进度时，多久之内快速到达实际进度
    private val ACCELERATE_INTERVAL = config.accelerateTime

    private var mListener:IProgressListener ?= null

    private var mCanceled = false

    private var  startTime:Long = 0

    var mRealProgress: Int = 0
        set(value) {
            field = value
            Logger.d("ProgressManager update realProgress:$value")
        }

    override fun run() {
        startTime = System.currentTimeMillis()
        resetData()
        while (mCountProgress < 100 && !mCanceled) {
            mCountProgress++
            //真是进度已经走完了
            if (mRealProgress >= 100) {
                //差的百分比
                val restProgress = 100 - mCountProgress
                //当前已经花了多长时间
                val costTime = System.currentTimeMillis() - startTime
                Logger.d("mCompleted111 mCountProgress:$mCountProgress,mRealProgress:${mRealProgress},restProgress:$restProgress, costTime:$costTime")


                if (restProgress <= 0){
                    continue
                }
                //如果花的时间小于最小时间，则按最小时间计算当前应该睡眠的时间，否则，快速加速到百分之百吧
                val sleepInterval = if (costTime < config.minThresold)  (config.minThresold - costTime) / restProgress else ACCELERATE_INTERVAL / restProgress

                Logger.d("mCompleted222 mCountProgress:$mCountProgress,mRealProgress:${mRealProgress},restProgress:$restProgress, costTime:$costTime,sleepInterval:$sleepInterval")
                Thread.sleep(sleepInterval.toLong())

            } else {
                val progressDiff = mCountProgress - mRealProgress
                //如果展示进度小于实际任务进度,则将展示进度加快(300 ms内加速赶上); 否则按照计算的间隔休眠
                val sleepInterval = if (progressDiff < 0) (ACCELERATE_INTERVAL / abs(progressDiff)) else DEFAULT_INTERVAL
                Logger.d("not completed mCountProgress:$mCountProgress,mRealProgress:$mRealProgress, progressDiff:$progressDiff,sleepInterval:$sleepInterval")
                Thread.sleep(sleepInterval.toLong())
            }
            mListener?.onProgress(mCountProgress)
        }
        onEnd()

        Logger.d("total cost time:${System.currentTimeMillis() - startTime} ms")

    }

    fun start(){
        mListener?.onStart()
        this.run()
    }

    fun setProgressListener(listener: IProgressListener){
        this.mListener = listener
    }

    private fun onEnd(){
        mListener?.onFinish()
    }

    fun costTime():Long{
        return System.currentTimeMillis() - startTime
    }

    private fun resetData(){
        mCountProgress = 0
        mRealProgress = 0
        mCanceled = false
    }

    fun stop(){
        mCanceled = true
        resetData()

    }



}