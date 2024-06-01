package com.tesla.framework.component

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.MessageQueue
import java.lang.ref.WeakReference
import java.util.LinkedList

/**
 * Created by Jerry on 2021/11/15.
 * 利用 IdleHandler 特效，线程空闲时执行
 */
class DelayInitTaskDispatcher {

    private val mDelayTasks  = LinkedList<WeakReference<ITask>>()

    private val mMainHandler = Handler(Looper.getMainLooper())

    private val mIdleHandler = MessageQueue.IdleHandler {
        if (mDelayTasks.size > 0){
            val task = mDelayTasks.poll()
            task.get()?.execute()
        }
        //Return true to keep your idle handler active, false  to have it removed
        //mDelayTasks 没有元素时返回 false 保证
        return@IdleHandler !mDelayTasks.isEmpty()
    }

    fun addTask(task: ITask): DelayInitTaskDispatcher {
        val reference = WeakReference(task)
        mDelayTasks.add(reference)
        return this
    }

    fun removeTask(task: ITask):Boolean{
        val iterator = mDelayTasks.iterator()
        while (iterator.hasNext()){
            val next = iterator.next()
            if (next.get() != null && next.get() == task){
                iterator.remove()
                return true
            }
        }
        return false
    }

    fun start() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            setupHandler(Looper.myQueue())
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                setupHandler(Looper.getMainLooper().queue)
            } else {
                mMainHandler.post(Runnable { setupHandler(Looper.myQueue()) })
            }
        }
    }

    private fun setupHandler(queue: MessageQueue) {
        queue.addIdleHandler(mIdleHandler)
    }


}