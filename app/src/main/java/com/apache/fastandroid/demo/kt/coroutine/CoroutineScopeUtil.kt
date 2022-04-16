package com.apache.fastandroid.demo.kt.coroutine

import com.apache.fastandroid.network.retrofit.RetrofitFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

/**
 * Created by Jerry on 2022/4/10.
 */
object CoroutineScopeUtil {
    private val coroutineScope = CoroutineScope(Job() + Dispatchers.Main)

    fun coroutineScopeRequest() {
        val job1 = coroutineScope.launch {
            try {
                val result1 = RetrofitFactory.instance.apiService.getArticleById(21613)
                println("coroutineScope onSuccess result1:$result1, thread:${Thread.currentThread().name}")
            }catch (ex: Exception){
                ex.printStackTrace()
                println("coroutineScope onFailed: ${ex.message}, thread:${Thread.currentThread().name}")

            }

        }


    }

}