package com.apache.fastandroid.demo.component.countdown

/**
 * Created by Jerry on 2024/6/8.
 */
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class CountdownViewModel : ViewModel() {

    private val _countdownState = MutableLiveData<CountdownState>()
    val countdownState: LiveData<CountdownState> = _countdownState

    private var countdownJob: Job? = null

    fun startCountdown(duration: Long) {
        cancelCountdown() // 取消之前的倒计时
        countdownJob = viewModelScope.launch {
            var time = duration
            while (time > 0) {
                if (!viewModelScope.isActive) break
                _countdownState.value = CountdownState(time, true, false)
                delay(1000) // 每秒更新一次
                time--
            }
            _countdownState.value = CountdownState(0, false, false)
        }
    }

    fun pauseCountdown() {
        viewModelScope.launch {
            if (countdownJob?.isActive == true) {
                countdownJob?.cancel()
                _countdownState.value = _countdownState.value?.copy(isPaused = true)
            }
        }
    }

    fun resumeCountdown() {
        viewModelScope.launch {
            if (_countdownState.value?.isPaused == true) {
                val remainingTime = _countdownState.value?.remainingTime ?: 0
                startCountdown(remainingTime)
                _countdownState.value = _countdownState.value?.copy(isPaused = false)
            }
        }
    }

    fun restartCountdown(duration: Long) {
        viewModelScope.launch {
            startCountdown(duration)
            _countdownState.value = _countdownState.value?.copy(isRunning = true, isPaused = false)
        }
    }

    fun stopCountdown() {
        viewModelScope.launch {
            cancelCountdown()
            _countdownState.value = CountdownState(0, false, false)
        }
    }

    private fun cancelCountdown() {
        countdownJob?.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        cancelCountdown()
    }
}