package com.apache.fastandroid.util.extensitons

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apache.fastandroid.demo.component.loadsir.callback.EmptyCallback
import com.apache.fastandroid.demo.component.loadsir.callback.LoadingCallback
import com.blankj.utilcode.util.ThreadUtils
import com.kingja.loadsir.core.LoadService

/**
 * Created by Jerry on 2021/12/16.
 */
fun LoadService<Any>.showLoading(){
    showCallback(LoadingCallback::class.java)
}

fun LoadService<Any>.showEmpty(){
    showCallback(EmptyCallback::class.java)
}

fun runOnUIDelay(block: () -> Unit, delay:Long){
    ThreadUtils.runOnUiThreadDelayed({
        block.invoke()
    },delay)
}

fun runOnUi(block: () -> Unit){
    ThreadUtils.runOnUiThread { block.invoke() }
}


//LCE -> Loading/Content/Error
sealed class PageState<out T> {
    data class Success<T>(val data: T) : PageState<T>()
    data class Error<T>(val message: String) : PageState<T>() {
        constructor(t: Throwable) : this(t.message ?: "")
    }
}

fun <T> MutableLiveData<T>.asLiveData(): LiveData<T> {
    return this
}

sealed class FetchStatus {
    object Fetching : FetchStatus()
    object Fetched : FetchStatus()
    object NotFetched : FetchStatus()
}