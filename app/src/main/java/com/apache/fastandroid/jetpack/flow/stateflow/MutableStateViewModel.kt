package com.apache.fastandroid.jetpack.flow.stateflow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tesla.framework.component.Event
import com.tesla.framework.component.LocalEventBus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

/**
 * Created by Jerry on 2022/6/20.
 */
class MutableStateViewModel:ViewModel() {

    val mCount = MutableStateFlow(0)

    fun increment(){
       mCount.apply {
           var current = ++value
           value =  current.coerceAtMost(10)
       }
    }

    fun decrement(){
        mCount.apply {
            value =  (--value).coerceAtLeast(0)
        }
    }


    private lateinit var job: Job

    fun startRefresh() {
        job = viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                LocalEventBus.postEvent(Event(System.currentTimeMillis()))
                delay(100)
            }
        }
    }

    fun stopRefresh() {
        job.cancel()
    }
}