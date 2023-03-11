package com.apache.fastandroid.demo.elegant

import com.tesla.framework.component.logger.Logger
import kotlinx.coroutines.*
import java.util.UUID

/**
 * Created by Jerry on 2023/3/11.
 */
object AndroidClient {

    private val mListeners = arrayListOf<IClientChangeListener>()


    fun addClientChangeListener(listener:IClientChangeListener){
        mListeners.add(listener)
    }

    fun removeClientChangeListener(listener:IClientChangeListener){
        mListeners.remove(listener)
    }


    fun trigger(){
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO){
                delay(2000)
            }
            for (i in 0 until mListeners.size){
                mListeners[i].onClientChanged("I am random number:${UUID.randomUUID()}")
            }
        }
    }


    interface IClientChangeListener{
        fun onClientChanged(result:String)
    }

}