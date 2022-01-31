package com.apache.fastandroid.demo.kt.coroutine

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentKotlinCouritine2Binding
import com.apache.fastandroid.databinding.FragmentKotlinCouritineBinding
import com.apache.fastandroid.network.retrofit.ApiEngine
import com.apache.fastandroid.network.retrofit.ApiServiceKt
import com.apache.fastandroid.network.retrofit.convertor.CustomGsonConverterFactory
import com.apache.fastandroid.util.extensitons.runOnUi
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.ui.fragment.BaseLifecycleFragment
import com.tesla.framework.ui.fragment.BaseVMFragment
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_kotlin_couritine.*
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import java.lang.Exception
import kotlin.concurrent.thread
import kotlin.jvm.internal.Intrinsics

/**
 * Created by Jerry on 2021/10/28.
 * 协程
 */
class CouroutineDemoFragment2:BaseVMFragment<FragmentKotlinCouritine2Binding>(FragmentKotlinCouritine2Binding::inflate) {
    companion object{
        private const val TAG = "CouroutineDemoFragment"
    }


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        viewBinding.btnLaunchCoroutine.setOnClickListener {
            runBlocking {
                Logger.d("runBlocking thread: ${Thread.currentThread().name}")
                
                val job = launch {
                    println("job thread: ${Thread.currentThread().name}")

                    repeat(1000){

                        println("挂起中:$it")
                        delay(500L)
                    }
                }
                delay(1300)
                println("main:主线程等待中")
                job.cancel()
//                job.join()
                println("main:主线程即将完成退出")

            }
        }

        viewBinding.btnAsyncAwait.setOnClickListener {
            runBlocking {
                val job:Deferred<String> = async {
                    delay(500)
                    return@async "hello"
                }
                val result = job.await()
                println("result:$result")


                launch {

                }

            }
        }

        viewBinding.btnCoroutineLaunchParam.setOnClickListener {

        }

        viewBinding.btnSuspendContinuation.setOnClickListener {
            GlobalScope.launch {
                LaunchCoroutine.testSuspendContinuation()
            }
        }

        viewBinding.btnCouroutineTheory.setOnClickListener {
            testCoroutineTheory()
        }
    }

    private fun testCoroutineTheory(){
        MainScope().launch {
            val job = async {
                "string"
            }
            val result = job.await()
            println(result)
        }
    }

}

