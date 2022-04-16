package com.apache.fastandroid.demo.kt.coroutine

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentCoroutineCancelTimeoutBinding
import com.apache.fastandroid.databinding.FragmentCoroutineSuspendBinding
import com.tesla.framework.ui.fragment.BaseVBFragment
import kotlinx.coroutines.*
import java.lang.ArithmeticException
import kotlin.system.measureTimeMillis

/**
 * Created by Jerry on 2021/10/28.
 * 协程取消
 * 参考：https://book.kotlincn.net/text/composing-suspending-functions.html
 */
class CoroutineSuspendFuncDemoFragment :
    BaseVBFragment<FragmentCoroutineSuspendBinding>(FragmentCoroutineSuspendBinding::inflate) {
    companion object {
        private const val TAG = "CoroutineCancelTimeoutDemoFragment"
    }


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)


        mBinding.btnDefaultOrder.setOnClickListener {
            defaultCallOrder()
        }
        mBinding.btnAsyncAwait.setOnClickListener {
            asyncAwait()
        }
        mBinding.btnLazyStartAsync.setOnClickListener {
            lazyStartAsync()
        }
        mBinding.btnAsyncStyle.setOnClickListener {
            asynStyle()
        }

        mBinding.btnAsyncConcurrency.setOnClickListener {
            asynConstructorConcurrency()
        }
        mBinding.btnChildCoroutineFailed.setOnClickListener {
            childCoroutineFailed()
        }
    }


    var acquired = 0

    class Resource {
//        init { acquired = 0 } // Acquire the resource
//        fun close() { acquired-- } // Release the resource
    }

    private fun asyncTimeoutResource() {
        runBlocking {
            repeat(100_00){
                launch {
                    val resource = withTimeout(60){
                        delay(50)
//                        Resource()
                        acquired ++
                    }
//                    resource.close()
                    acquired--
                }

            }
        }
        println("acquired:${acquired}")
    }

    /**
     * 超过给定的时间任务还没执行完会抛出 TimeoutCancellationException
     *  需要手动捕获
     */
    private fun withTimeout() {
        runBlocking {
            try {
                withTimeout(1500){
                    repeat(1000){
                        println("I am sleeping ${it}")
                        delay(500)
                    }
                }
            }catch (e:Exception){
                e.printStackTrace()
            }

        }
    }

    private fun withTimeoutOrNull() {
        runBlocking {
            val result = withTimeoutOrNull(1500){
                repeat(1000){
                    println("I am sleeping ${it}")
                    delay(500)
                }
                "Done"
            }
            println("result is :${result}")

        }
    }


    private fun childCoroutineFailed() {
        runBlocking {
            try {
                failedConcurrentSum()
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    /**
     * 如果在 concurrentSum 函数内部发生了错误，并且它抛出了一个异常，
     * 所有在作用域中启动的协程都会被取消。
     * 如果其中一个子协程（即 two）失败，第一个 async 以及等待中的父协程都会被取消：
     */
    suspend fun failedConcurrentSum():Int = coroutineScope{
        val one = async<Int> {
            try {
                delay(Long.MAX_VALUE)
                println("first child run")
                42
            }finally {
                println("First child was canceled")
            }

        }
        val two = async<Int> {
            println("second child throws excepiton")
            throw ArithmeticException()
        }

        one.await() + two.await()
    }



    /**
     * 如果在 concurrentSum 函数内部发生了错误，并且它抛出了一个异常，
     * 所有在作用域中启动的协程都会被取消。
     */
    private fun asynConstructorConcurrency() {
        runBlocking {
            val time = measureTimeMillis {
                println("the answer is: ${concurrentSum()}")
            }
            println("Completed in ${time} ms")
        }

    }


    private suspend fun concurrentSum():Int = coroutineScope {
        val one = async { doSomethingUsefulOne() }
        val two = async { doSomethingUsefulTwo() }
        one.await() + two.await()
    }

    /**
     * 这些 xxxAsync 函数不是 挂起 函数。它们可以在任何地方使用。 然而，
     * 它们总是在调用它们的代码中意味着异步（这里的意思是 并发 ）执行。
     * 这种带有异步函数的编程风格仅供参考，因为这在其它编程语言中是一种受欢迎的风格。在 Kotlin 的协程中使用这种风格是强烈不推荐的， 原因如下所述。


     */
    private fun asynStyle() {
        val time = measureTimeMillis {
            // 我们可以在协程外面启动异步执行
            val one = doSomethingUsefulOneAsync()
            val two = doSomethingUsefulOneAsync()
            // 但是等待结果必须调用其它的挂起或者阻塞
            // 当我们等待结果的时候，这里我们使用 `runBlocking { …… }` 来阻塞主线程
            runBlocking {
                println("the answer is ${one.await() + two.await()}")
            }
        }
        println("Completed in ${time} ms")

    }

    private fun lazyStartAsync() {
        runBlocking {
            val time = measureTimeMillis {
                val one = async(start = CoroutineStart.LAZY) { doSomethingUsefulOne() }
                val two = async(start = CoroutineStart.LAZY) { doSomethingUsefulTwo() }
                one.start()
                two.start()
                println("the answer is:${one.await() + two.await()}")
            }
            println("Completed in ${time} ms")

        }
    }

    private fun asyncAwait() {
        runBlocking {

            val time = measureTimeMillis {
                val deferredOne = async { doSomethingUsefulOne() }
                val deferredTwo = async { doSomethingUsefulTwo() }
                val one = deferredOne.await()
                println("one:${one}")
                val tow = deferredTwo.await()
                println("two:${tow}")

                println("the answer is ${one + tow}")
            }
            println("Completed in ${time} ms")
        }

    }


    private fun defaultCallOrder() {
        runBlocking {
            val time = measureTimeMillis {
                val one = doSomethingUsefulOne()
                val two = doSomethingUsefulTwo()
                println("the answer is ${one + two}")
            }
            println("Completed in ${time} ms")
        }
    }

    fun doSomethingUsefulOneAsync() = GlobalScope.async {
        doSomethingUsefulOne()
    }

    fun doSomethingUsefulTwoAsync() = GlobalScope.async {
        doSomethingUsefulTwo()
    }









    suspend fun doSomethingUsefulOne(): Int {
        delay(1000)
        return 13
    }

    suspend fun doSomethingUsefulTwo(): Int {
        delay(1000)
        return 29
    }

    private fun makeCalculateCancable() {
        runBlocking {
            try {
                val startTime = System.currentTimeMillis()
                val job = launch(Dispatchers.Default) {
                    var nextPrintTime = startTime
                    var i = 0
                    while (isActive) {
                        if (System.currentTimeMillis() >= nextPrintTime) {
                            println("Job: I'm sleeping ${i++}")
                            nextPrintTime += 500
                        }
                    }
                }

                delay(1300)
                println("I am tired of waiting")
                job.cancelAndJoin()
                println("Main: Now I can quit")
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                println("finaly")
            }
        }
    }

    /**
     * 协程的取消是 协作 的。一段协程代码必须协作才能被取消。 所有 kotlinx.coroutines 中的挂起函数都是 可被取消的 。它们检查协程的取消， 并在取消时抛出 CancellationException。 然而，如果协程正在执行计算任务，并且没有检查取消的话，那么它是不能被取消的，就如如下示例代码所示：
     */
    private fun cancelIsCooperation() {
        runBlocking {
            try {
                val startTime = System.currentTimeMillis()
                val job = launch(Dispatchers.Default) {
                    var nextPrintTime = startTime
                    var i = 0
                    while (i < 5) {
                        if (System.currentTimeMillis() >= nextPrintTime) {
                            println("Job: I'm sleeping ${i++}")
                            nextPrintTime += 500
                        }
                    }
                }

                delay(1300)
                println("I am tired of waiting")
                job.cancelAndJoin()
                println("Main: Now I can quit")
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                println("finaly")
            }
        }
    }

    private fun cancelJob() {
        runBlocking {
            val job = launch {
                repeat(1000) { i ->
                    println("Job: I'm sleeping ${i}")
                    delay(500)
                }
            }

            delay(1300)
            println("I am tired of waiting!")
//            job.cancel() //取消该作业
//            job.join() //等待作业执行结束

            job.cancelAndJoin()
            println("main:Now I can quit")
        }
    }


}

