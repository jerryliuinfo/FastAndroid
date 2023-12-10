package com.apache.fastandroid.jetpack.flow.vm

import androidx.lifecycle.ViewModel
import com.tesla.framework.component.logger.Logger
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import java.util.Timer
import java.util.TimerTask

/**
 * Created by Jerry on 2023/12/10.
 */
class FlowShareDemoViewModel : ViewModel() {

    val coldFlow = flow<Int> {
        emit(1)
        Logger.d("emit 1 thread:${Thread.currentThread().name}")
        delay(50)
        emit(2)
        Logger.d("emit 2 thread:${Thread.currentThread().name}")

        emit(3)
        emit(4)
    }



    private val _stateFlow = MutableStateFlow<Int> (0)
     val stateFlow = _stateFlow


    fun startCount(){
        val timer = Timer()
        timer.scheduleAtFixedRate(object :TimerTask(){
            override fun run() {
                _stateFlow.value += 1
            }

        },0,1000)
    }


    private val _sharedFlow = MutableSharedFlow<Int>(
        replay = 0,
        extraBufferCapacity = 0,
        onBufferOverflow = BufferOverflow.SUSPEND
    )
    val sharedFlow: SharedFlow<Int> = _sharedFlow


}