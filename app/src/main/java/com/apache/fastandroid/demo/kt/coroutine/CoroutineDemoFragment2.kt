package com.apache.fastandroid.demo.kt.coroutine

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentKotlinCouritine2Binding
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.ui.fragment.BaseVBFragment
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel

/**
 * Created by Jerry on 2021/10/28.
 * 协程
 */
class CoroutineDemoFragment2:BaseVBFragment<FragmentKotlinCouritine2Binding>(FragmentKotlinCouritine2Binding::inflate) {
    companion object{
        private const val TAG = "CoroutineDemoFragment"
    }


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.btnLaunchCoroutine.setOnClickListener {
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

        mBinding.btnAsyncAwait.setOnClickListener {
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

        mBinding.btnCoroutineLaunchParam.setOnClickListener {

        }

        mBinding.btnSuspendContinuation.setOnClickListener {
            GlobalScope.launch {
                LaunchCoroutine.testSuspendContinuation()
            }
        }

        mBinding.btnCouroutineTheory.setOnClickListener {
            testCoroutineTheory()
        }

        mBinding.btnChannel.setOnClickListener {
            testChannel()
        }

        mBinding.btnChannelBus.setOnClickListener {
            "hello".post()

            onEvent { str:String ->
                println("receive :$str")
            }
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


    private fun testChannel(){
        val channel = Channel<Int>()
        GlobalScope.launch {
            launch {
                get(channel)
            }
            launch {
                put(channel)
            }
            delay(3000)
        }

        Thread.sleep(2000)
        channel.cancel()

    }

    suspend fun get(channel:Channel<Int>){
        while (true){
            println(channel.receive())
        }
    }

    suspend fun put(channel:Channel<Int>){
        var i = 0
        while (true){
            channel.send(i++)
        }
    }
}

