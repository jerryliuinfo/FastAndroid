package com.apache.fastandroid.demo.task

import android.os.Handler
import android.os.Looper
import com.tesla.framework.component.logger.Logger

/**
 * Created by Jerry on 2022/2/3.
 */
class ExecuteTimerMonitor {

    private val mExecuteTimeMap = mutableMapOf<String,Long>()

    private var mStartTime:Long = 0;

    var mCostTime:Long = 0


    private var mUIHandler:Handler ?= null
        get() {
            if (field == null){
                field = Handler(Looper.getMainLooper())
            }
            return field
        }

    fun recordStart(){
        mStartTime = System.currentTimeMillis()
    }

    fun recordFinish(){
        mCostTime = System.currentTimeMillis() - mStartTime
    }

    fun record(taskName:String, executeTime:Long){
        if (executeTime > MonitorConfig.WARNING_TIME){
            Logger.w("task:${taskName} runs too long, cost time:${executeTime}")
        }
        mExecuteTimeMap[taskName] = executeTime
    }
}