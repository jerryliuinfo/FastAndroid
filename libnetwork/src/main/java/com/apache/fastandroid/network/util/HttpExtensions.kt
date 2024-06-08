package com.apache.fastandroid.network.util

import com.apache.fastandroid.network.model.Resource
import com.tesla.framework.component.logger.Logger
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/**
 * 协程发起网络请求，并在 Main 线程回调
 * @param scope CoroutineScope
 * @param callback Function1<Resource<T>, Unit>
 * @param dispatcher CoroutineDispatcher
 * @param handler CoroutineExceptionHandler?
 * @param block [@kotlin.ExtensionFunctionType] SuspendFunction1<CoroutineScope, T>
 * @return Job
 */
fun <T> request(
    scope: CoroutineScope,
    callback: (Resource<T>) -> Unit,
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    handler: CoroutineExceptionHandler? = null,
    block: suspend CoroutineScope.() -> T
): Job = scope.launch(dispatcher) {
    try {
        val result = block()
        withContext(Dispatchers.Main) {
            callback.invoke(Resource.success(result))
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Logger.e("Http Request Exception message %s", e.message)
        withContext(Dispatchers.Main) {
            handler?.handleException(this.coroutineContext, e)
            callback.invoke(Resource.error(e.message ?:"unknow error", null))

        }
    }
}


