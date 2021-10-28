package com.apache.fastandroid.demo.kt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apache.fastandroid.home.HomeReporsitory
import com.tesla.framework.common.util.log.NLog
import kotlinx.coroutines.*

/**
 * Created by Jerry on 2021/10/28.
 */
class CoroutineVewModel: ViewModel() {
    companion object{
        private const val TAG = "CoroutineVewModel"
    }

    private val viewModelJob = SupervisorJob()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun loadByViewModel(){
        uiScope.launch() {
            kotlin.runCatching {
                withContext(Dispatchers.IO){
                    Thread.sleep(5000)
                    NLog.d(TAG, "loadByViewModel run thread: %s",Thread.currentThread().name)
                    HomeReporsitory.newInstance().loadHomeArticleCoSync(1)
                }
            }.onSuccess {
                NLog.d(TAG, "loadByViewModel onSuccess thread: %s, data: %s",Thread.currentThread().name,it.size)
            }.onFailure {
                NLog.d(TAG, "loadByViewModel onFailed thread: %s, error: %s",Thread.currentThread().name,it)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        NLog.d(TAG, "onCleared viewModelJob.cancel()--->")
        viewModelJob.cancel()
    }


}