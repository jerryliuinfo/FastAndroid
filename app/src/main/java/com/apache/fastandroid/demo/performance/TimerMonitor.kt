package com.apache.fastandroid.demo.performance

/**
 * Created by Jerry on 2024/4/6.
 */
class TimerMonitor(val id:Int = -1) {

    private val mTimerTag = hashMapOf<String,Long>()

    private var mStartTime:Long = 0

    fun startMonitor(){
        if (mTimerTag.isNotEmpty()){
            mTimerTag.clear()
        }
        mStartTime = System.currentTimeMillis()
    }
    

    fun recordTimerTag(tag:String){
        if (mTimerTag[tag] != null){
            mTimerTag.remove(tag)
        }
    }
}