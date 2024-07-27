package com.apache.fastandroid.demo.kt.coroutine

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.apache.fastandroid.databinding.FragmentCoroutineBasicBinding
import com.tesla.framework.ui.fragment.BaseBindingFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.concurrent.thread

/**
 * Created by Jerry on 2021/10/28.
 * 协程基础
 * 参考：https://book.kotlincn.net/text/coroutines-basics.html
 */
class CoroutineBasicDemoFragment:BaseBindingFragment<FragmentCoroutineBasicBinding>(FragmentCoroutineBasicBinding::inflate) {
    companion object{

    }

    private val viewModel:CoroutineVewModel by viewModels()

    private val mainScope = MainScope()


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)




        mBinding.btnFirstCoroutine.setOnClickListener {
            firstCoroutineExample()
        }
        mBinding.btnSuspend.setOnClickListener {
            suspendFunction()
        }
        mBinding.btnCoroutineScope.setOnClickListener {
            coroutineScope()
        }
        mBinding.btnJobJoin.setOnClickListener {
            jobJoin()
        }
        mBinding.btnPerformanceCoroutine.setOnClickListener {
            performanceCoroutine()
        }
        mBinding.btnPerformanceThread.setOnClickListener {
            performanceThread()
        }
        mBinding.btnCancelTimeout.setOnClickListener {
            cancelTimeout()
        }
        mBinding.btnEnsureActive.setOnClickListener {
            ensureActive()
        }
        mBinding.btnDoInFinaly.setOnClickListener {
            doInFinaly()
        }
        mBinding.btnNonCancable.setOnClickListener {
            nonCancellable()
        }
    }

    private fun ensureActive() {
        runBlocking {
            //sampleStart
            val startTime = System.currentTimeMillis()
            val job = launch(Dispatchers.Default) {
                var nextPrintTime = startTime
                var i = 0
                while (isActive) { // 可以被取消的计算循环
//                while (true) { // 可以被取消的计算循环
//                    ensureActive()
                    // 每秒打印消息两次
                    if (System.currentTimeMillis() >= nextPrintTime) {
                        println("job: I'm sleeping ${i++} ...")
                        nextPrintTime += 500L
                    }
                }
            }
            delay(1300L) // 等待一段时间
            println("main: I'm tired of waiting!")
            job.cancelAndJoin() // 取消该作业并等待它结束
            println("main: Now I can quit.")
            //sampleEnd
        }
    }

    private fun nonCancellable(){
        runBlocking {
            val job = launch {
                try {
                    repeat(1000) { i ->
                        println("job: I'm sleeping $i ...")
                        delay(500L)
                    }
                } finally {
                    withContext(NonCancellable) {
                        println("job: I'm running finally")
                        delay(1000L)
                        println("job: And I've just delayed for 1 sec because I'm non-cancellable")
                    }
                }
            }
            delay(1300L) // 延迟一段时间
            println("main: I'm tired of waiting!")
            job.cancelAndJoin() // 取消该作业并等待它结束
            println("main: Now I can quit.")
        }
    }


    private fun doInFinaly(){
        runBlocking {
            val job = launch {
                try {
                    repeat(1000) { i ->
                        println("job: I'm sleeping $i ...")
                        delay(500L)
                    }
                } finally {
                    println("job: I'm running finally")
                }
            }
            delay(1300L) // 延迟一段时间
            println("main: I'm tired of waiting!")
            job.cancelAndJoin() // 取消该作业并且等待它结束
            println("main: Now I can quit.")
        }
    }

    private fun cancelTimeout() {
        runBlocking {
            val job = launch {
                repeat(10000){
                    println("Job: I am sleeping ${it}")
                    delay(500)
                }
            }
            delay(1300)
            println("main: I'm tired of waiting!")
            job.cancel()
            job.join()
            println("Now ,I can quit ")
        }
    }


    private fun performanceCoroutine() {
        runBlocking {
            launch {
                repeat(100_100) {
                    delay(2000)
                    println(it)
                }
            }
        }

    }
    private fun performanceThread() {
        runBlocking {
            thread {
                for (i in 0 until 100_100 ){
                    Thread.sleep(2000)
                    println(i)
                }

            }
        }
    }



    private fun jobJoin() {
        mainScope.launch {
            val job = launch {
                delay(5000)
                println("world")
            }
            println("Hello")
            job.join() //等待子协程执行完成
            println("Done ")
        }
        viewModel.testJob()
    }

    /**
     *
     *  runBlocking 方法阻塞了当前等待的线程，而 coroutineScope 只是暂停，
     *  释放了用于其他用途的底层线程。因为这个区别，runBlocking 是一个常规函数，coroutescope 是一个挂起函数。
     */
    private fun coroutineScope(){
       runBlocking {
           doWorld2()
           println("Done")
       }
    }

    suspend fun doWorld2() = coroutineScope { // this: CoroutineScope
        launch {
            delay(2000L)
            println("World 2 thread:${Thread.currentThread().name}")
        }
        launch {
            delay(1000L)
            println("World 1:${Thread.currentThread().name}")
        }
        println("Hello")
    }

    private fun firstCoroutineExample() {
        runBlocking {
            launch {
                delay(1000)
                println("World")
            }
            println("Hello")
        }

    }
    private fun suspendFunction() {
        runBlocking {
           launch {
               doWorld()
           }
            println("Hello")
        }
        lifecycleScope.launch {
            launch {
                doWorld()
            }
            println("Hello2")
        }
    }


    private suspend fun doWorld(){
//        delay(1000)
        println("World")
    }

}

