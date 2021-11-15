package com.apache.fastandroid.demo.designmode.idlehandler

import java.util.concurrent.CopyOnWriteArrayList

/**
 * Created by Jerry on 2021/11/15.
 */
class MyMessageQueue {


    var mIdleHandlers: CopyOnWriteArrayList<OnIdleHandler> = CopyOnWriteArrayList()

    fun addIdleHandler(idleHandler: OnIdleHandler){
        if (idleHandler == null){
            throw NullPointerException("can not add a null handler")
        }
        synchronized(this){
            mIdleHandlers.add(idleHandler)
        }
    }

    fun removeHandler(idleHandler: OnIdleHandler){
        synchronized(this){
            mIdleHandlers.remove(idleHandler)
        }
    }


    fun next(){
        var size = mIdleHandlers.size
        for (i in 0 until size){
            var keep = false
            try {
                keep = mIdleHandlers[i].queueIdle()
            }catch (e:Exception ){
                e.printStackTrace()
            }
            if (!keep){
                mIdleHandlers.removeAt(i)
            }

        }
    }

    interface OnIdleHandler {
        /**
         * Called when the message queue has run out of messages and will now
         * wait for more.  Return true to keep your idle handler active, false
         * to have it removed.  This may be called if there are still messages
         * pending in the queue, but they are all scheduled to be dispatched
         * after the current time.
         */
        fun queueIdle(): Boolean
    }
}