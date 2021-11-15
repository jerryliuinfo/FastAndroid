package com.tesla.framework.common.util

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.MessageQueue
import android.util.Log
import android.view.View
import androidx.annotation.IntRange
import com.tesla.framework.BuildConfig
import com.tesla.framework.common.util.log.NLog
import java.lang.StringBuilder

/**
 * Created by Jerry on 2021/11/15.
 */
object DrakeetUtils {
    private const val TAG = "DrakeetUtils"

    fun stackTrace(self:Any? = null, @IntRange(from = 2) limit:Int = 3):String?{
        if (!BuildConfig.DEBUG){
            return null
        }
        val result = StringBuilder()
        var length = 0
        for ((i:Int,it:StackTraceElement) in Thread.currentThread().stackTrace.withIndex()){
            //过滤掉前几个调用链
            if (i < 4 || it.methodName.contains("\$default")){
                continue
            }
            if (length > 0) result.append(" <- ")
            if (self == null || self.javaClass.name != it.className){
                result.append(it.methodName).append(".")
            }
            result.append(it.methodName.replace("\$app_debug", "") + "(${it.lineNumber})")
            length++
            if (length >= limit) break
        }
        return result.toString()
    }

    fun isAndroid12():Boolean{
        //Build.VERSION.PREVIEW_SDK_INT > 0 不要写在 Build.VERSION.SDK_INT == 30 前面，因为 6.0以下没有这个常量
        return (Build.VERSION.SDK_INT == 30  && Build.VERSION.PREVIEW_SDK_INT > 0) || Build.VERSION.SDK_INT == 31
    }


    fun View.doOnMainThreadIdle(action:() -> Unit, timeout:Long? = null){
//        val handler = Handler(Looper.getMainLooper())
        val idleHandler = MessageQueue.IdleHandler {
            //移除所有消息，避免post delay消息会被执行
            handler.removeCallbacksAndMessages(null)
            NLog.d(TAG, "doAction -->")
            action()
            return@IdleHandler false
        }

        fun setupIdleHandler(queue: MessageQueue){
            if (timeout != null){
                handler.postDelayed({
                    queue.removeIdleHandler(idleHandler)
                    action()
                    if (BuildConfig.DEBUG){
                        NLog.d(TAG, "${timeout} ms timeout")
                    }
                },timeout)
            }else{
                queue.addIdleHandler(idleHandler)
            }
        }
        if (Looper.getMainLooper() == Looper.myLooper()){
            setupIdleHandler(Looper.myQueue())
        }else{
            //子线程
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                setupIdleHandler(Looper.getMainLooper().queue)
            }else{
                //6.0 以下不能能直接调 Looper.queue
                handler.post {
                    setupIdleHandler(Looper.myQueue())
                }
            }
        }

    }
}