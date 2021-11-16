package com.apache.fastandroid.performance

import android.os.SystemClock
import com.tesla.framework.common.util.log.NLog

/**
 * Created by Jerry on 2021/11/15.
 */
object TaskUtil {
    const val TAG = "task"
    
    fun runTask1(){
        NLog.d(TAG, "Task1 begin run")
        SystemClock.sleep(300);
        NLog.d(TAG, "Task1 run finished")
    }

    fun runTask2(){
        NLog.d(TAG, "Task2 begin run")
        SystemClock.sleep(300);
        NLog.d(TAG, "Task2 run finished")
    }

    fun runTask3(){
        NLog.d(TAG, "Task3 begin run")
        SystemClock.sleep(350);
        NLog.d(TAG, "Task3 run finished")
    }

    fun runTask4(){
        NLog.d(TAG, "Task4 begin run")
        SystemClock.sleep(200);
        NLog.d(TAG, "Task4 run finished")
    }

    fun runTask5(){
        NLog.d(TAG, "Task5 begin run")
        SystemClock.sleep(300);
        NLog.d(TAG, "Task5 run finished")
    }
    fun runTask6(){
        NLog.d(TAG, "Task6 begin run")
        SystemClock.sleep(150);
        NLog.d(TAG, "Task6 run finished")
    }
}