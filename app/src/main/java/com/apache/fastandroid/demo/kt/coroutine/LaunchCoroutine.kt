package com.apache.fastandroid.demo.kt.coroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

/**
 * Created by Jerry on 2022/1/31.
 */
object LaunchCoroutine {
     suspend fun testSuspendContinuation(){
        withContext(Dispatchers.IO){
            val job = async {
                delay(500)
                return@async "Hello"
            }
            val result = job.await()
            println("result:${result}")
        }
    }
}