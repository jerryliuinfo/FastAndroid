package com.apache.fastandroid.performance.anr

import android.app.ActivityManager
import android.content.Context
import android.os.Handler
import android.os.Looper
import com.apache.fastandroid.util.MainLogUtil
import com.tesla.framework.Global
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicInteger

/**
 * 根据bugly同学描述的检测anr的一种方法，
 */
object ANRMonitorJob {

    const val TAG = "ANRMonitorJob"
    const val ANR_INTERVAL = 5000L
    private val listenerList = CopyOnWriteArrayList<IANRMonitorListener>()
    @Volatile
    var mStart = false
    var mDefaultIntercept = 1000L
    var mThread: Thread? = null
    val mHandler = Handler(Looper.getMainLooper())
    @Volatile
    var mTick = 0L
    @Volatile
    var mResetTime = 0
    const val RESET_INTERVAL = 500L
    val mThreadInt = AtomicInteger(0)


    val mTickRunnable = Runnable {
        mTick = 0L
    }

    fun registerListener(listener: IANRMonitorListener) {
        listenerList.add(listener)
    }

    fun unregisterListener(listener: IANRMonitorListener) {
        listenerList.remove(listener)
    }

    @Synchronized
    fun startMonitor() {
        if (mThread == null || mThread?.isAlive != true || mThread?.isInterrupted != false) {
            mStart = true
            mThread = generateNewThread()
            mThread?.start()
        }
    }

    @Synchronized
    fun stopMonitor() {
        mStart = false
        mThread?.interrupt()
    }

    private fun generateNewThread(): Thread {
        return object : Thread("anr_monitor_${mThreadInt.incrementAndGet()}") {
            override fun run() {
                while (!isInterrupted && mStart) {
                    val needPost = mTick == 0L
                    if (needPost) {
                        mTick = System.currentTimeMillis()
                        mHandler.post(mTickRunnable)
                    }
                    try {
                        sleep(mDefaultIntercept)
                    } catch (e: InterruptedException) {
                        return
                    }

                    val tick = mTick
                    //在5s之内还没有UI线程还没有处理
                    if (tick != 0L && (System.currentTimeMillis() - tick > ANR_INTERVAL)) {
                        MainLogUtil.i("UI thread not handle yet, check")
                        val activityManager = Global.getSystemService(Context.ACTIVITY_SERVICE) as? ActivityManager
                        if (activityManager != null) {
                            var processInfo: ActivityManager.ProcessErrorStateInfo? = null
                            val processesInErrorState = activityManager.processesInErrorState
                            processesInErrorState?.let {
                                MainLogUtil.d( "processesInErrorState get success")
                                val iterator = it.iterator()
                                while (iterator.hasNext()) {
                                    val info = iterator.next()
                                    if (info.condition == ActivityManager.ProcessErrorStateInfo.NOT_RESPONDING) {
                                        MainLogUtil.d( "get anr success")
                                        processInfo = copyNewInfo(info)
                                        break
                                    }
                                }
                            }
                            processInfo?.let {
                                notifyANRHappen(it)
                                resetAnrInfo()
                            }
                        }
                    }
                }
                MainLogUtil.d( "Thread Stop success")
            }
        }
    }

    fun resetAnrInfo() {
        mResetTime = 0
        while (mResetTime < 2) {
            mTick = System.currentTimeMillis()
            mHandler.post(mTickRunnable)
            try {
                Thread.sleep(RESET_INTERVAL)
            } catch (e: InterruptedException) {
                return
            }
            if (mTick == 0L) {
                mResetTime++
                MainLogUtil.d( "reset success time: $mResetTime")
            } else {
                mResetTime = 0
            }
        }
    }

    private fun notifyANRHappen(processInfo: ActivityManager.ProcessErrorStateInfo) {
        for (listener in listenerList) {
            listener.onANRHappen(processInfo)
        }
    }


    private fun copyNewInfo(info: ActivityManager.ProcessErrorStateInfo): ActivityManager.ProcessErrorStateInfo {
        return ActivityManager.ProcessErrorStateInfo().apply {
            condition = info.condition
            processName = info.processName
            pid = info.pid
            uid = info.uid
            tag = info.tag
            shortMsg = info.shortMsg
            longMsg = info.longMsg
            stackTrace = info.stackTrace
        }
    }

    interface IANRMonitorListener {
        fun onANRHappen(processInfo: ActivityManager.ProcessErrorStateInfo)
    }
}