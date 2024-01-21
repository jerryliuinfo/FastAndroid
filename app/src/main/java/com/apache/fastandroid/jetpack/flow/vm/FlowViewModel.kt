package com.apache.fastandroid.jetpack.flow.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tesla.framework.component.logger.Logger
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import java.util.Timer
import java.util.TimerTask

/**
 * Created by Jerry on 2023/12/10.
 */
class FlowViewModel : ViewModel() {



    val timeFlow = flow {
        var time = 0
        while (true) {
            emit(time)
            Logger.d("timeFlow emit ${time},thread:${Thread.currentThread().name}")
            delay(1000)
            time++
        }
    }
    //旋转屏幕不会丢失数据
    val stateInTimerFlow =
        timeFlow.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            0
        )

    private val _stateFlow = MutableStateFlow<Int> (0)
     val stateFlow = _stateFlow

    /**
     * stateFlow 正常使用
     */
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