package com.apache.fastandroid.demo.kt.coroutine

import android.os.AsyncTask
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Runnable
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.coroutines.CoroutineContext

/**
 * Created by Jerry on 2022/1/30.
 */
object AndroidCommonPool:CoroutineDispatcher() {

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        AsyncTask.THREAD_POOL_EXECUTOR.execute(block)
    }
}