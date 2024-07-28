package com.apache.fastandroid.demo.kt.coroutine

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import com.apache.fastandroid.databinding.FragmentCoroutineBasicBinding
import com.tesla.framework.ui.fragment.BaseBindingFragment
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.concurrent.thread
import kotlin.system.measureTimeMillis

/**
 * Created by Jerry on 2021/10/28.
 * 协程基础
 * 参考：https://book.kotlincn.net/text/coroutines-basics.html
 */

var acquired = 0

class CoroutineBasicDemoFragment :
    BaseBindingFragment<FragmentCoroutineBasicBinding>(FragmentCoroutineBasicBinding::inflate) {
    companion object {

    }

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
        mBinding.btnCancelJob.setOnClickListener {
            cancelJob()
        }

        mBinding.btnCancelIsCooperative.setOnClickListener {
            cancelIsCooperative()
        }
        mBinding.btnCancelCooperativeCanceable.setOnClickListener {
            cancelIsCooperative2()
        }

        mBinding.btnEnsureActive.setOnClickListener {
            ensureActive()
        }
        mBinding.btnDoInFinally.setOnClickListener {
            doInFinally()
        }
        mBinding.btnNonCancable.setOnClickListener {
            nonCancellable()
        }

        mBinding.btnWithTimeout.setOnClickListener {
            timeout()
        }

        mBinding.btnWithTimeoutOrNull.setOnClickListener {
            withTimeoutOrNullUsage()
        }

        mBinding.btnAsynchronousTimeoutResources.setOnClickListener {
            asyncTimeoutResource()
        }

        mBinding.btnAsynchronousTimeoutResources2.setOnClickListener {
            asyncTimeoutResource2()
        }

        mBinding.btnSuspendSequenceExecute.setOnClickListener {
            sequenceExecute()
        }

        mBinding.btnSuspendAsync.setOnClickListener {
            asyncExecute()
        }

        mBinding.btnLazyAsync.setOnClickListener {
            lazyAsync()
        }

    }

    /**
     * async 可以通过将 start 参数设置为 CoroutineStart.LAZY 而变为惰性的。
     * 在这个模式下，只有结果通过 await 获取的时候协程才会启动，或者在 Job 的 start 函数调用的时候。
     */
    private fun lazyAsync() {
        mainScope.launch {
            val time = measureTimeMillis {
                val deferred1 = async(start = CoroutineStart.LAZY) { doSomething1() }
                val deferred2 = async(start = CoroutineStart.LAZY)  { doSomething2() }

                //加上了 start = CoroutineStart.LAZY 参数，则只有在

                deferred1.start()

                // println("The answer is ${deferred1.await() + deferred2.await()}")

            }
            println("Complete in $time ms")
        }
    }

    private fun asyncExecute() {
        mainScope.launch {
            val time = measureTimeMillis {
                val deferred1 = async { doSomething1() }
                val deferred2 = async { doSomething2() }
                println("The answer is ${deferred1.await() + deferred2.await()}")

            }
            println("Complete in $time ms")
        }
    }

    /**
     * 以下两个挂起函数按顺序执行，第 一个函数的结果可以用于第2个函数的参数条件
     * 总耗时为 2 个挂起函数的总耗时
     */
    private fun sequenceExecute() {
        mainScope.launch{
            val time = measureTimeMillis {
                val one = doSomething1()
                val two = doSomething2()
                println("The answer is ${one + two}")
            }
            println("Complete in $time ms")
        }
    }

    private suspend fun doSomething1():Int{
        println("begin execute doSomething1")
        delay(1000)
        return 10
    }

    private suspend fun doSomething2():Int{
        println("begin execute doSomething2")
        delay(2000)
        return 20
    }


    /**
     *例如，这里我们使用 Resource 类模拟可关闭资源，它通过递增获取的计数器和递减其 close 函数中的计数器来简单地跟踪创建了多少次。现在让我们创建许多协程，每个协程在 withTimeout 块的末尾创建一个 Resource，
     * 并在该块之外释放该资源。我们添加了一个小的延迟，以便更有可能在 with Timeout 块已经完成时超时发生，这将导致资源泄漏。
     */
    private fun asyncTimeoutResource() {
        runBlocking {
            repeat(100) {
                launch {
                    val resource = withTimeout(60) {
                        delay(100)
                        Resource()
                    }

                    resource.close()
                }
            }
        }
        // 这里输出的值不一定是 0
        println("acquired:$acquired")
    }


    /**
     * 要解决这个问题，可以在变量中存储对资源的引用，而不是从 withTimeout 块返回它。
     */
    private fun asyncTimeoutResource2() {
        runBlocking {
            repeat(1000) {
                launch {
                    var resource: Resource? = null
                    try {
                        withTimeout(60) {
                            delay(50)
                            resource = Resource()
                        }
                    } finally {
                        resource?.close()
                    }
                }
            }
        }
        // 这里输出的值不一定是 0
        println("acquired2:$acquired")
    }

    class Resource {

        init {
            acquired++
            println("resource init acquired:$acquired")
        }

        fun close() {
            acquired--
            println("resource close acquired:$acquired")

        }
    }

    private fun withTimeoutOrNullUsage() {
        runBlocking {
            val result = withTimeoutOrNull(1300L) {
                repeat(1000) { i ->
                    println("I'm sleeping $i ...")
                    delay(500L)
                }
            }
            println("end result is ${result}...")

        }
    }

    /**
     *
     */
    private fun timeout() {
        runBlocking {
            withTimeout(1300L) {
                repeat(1000) { i ->
                    println("I'm sleeping $i ...")
                    delay(500L)
                }
            }
            println("end...")

        }
    }

    /**
     * 能取消协程，因为 delay 是挂起函数，内部会检查协程的状态，发现协程被取消后，会终止协程内部的代码
     */
    private fun cancelIsCooperative2() {
        runBlocking {
            val job = launch(Dispatchers.Default) {
                repeat(5) { i ->
                    // 不需要 catch，catch 会导致协程无法被取消
                    /* try {
                        // print a message twice a second
                        println("job: I'm sleeping $i ...")
                        delay(500)
                    } catch (e: Exception) {
                        // log the exception
                        println(e)
                    }
 */
                    println("job: I'm sleeping $i ...")
                    delay(50)
                }
            }
            delay(1300L) // delay a bit
            println("main: I'm tired of waiting!")
            job.cancelAndJoin() // cancels the job and waits for its completion
            println("main: Now I can quit.")
        }
    }

    /**
     * 协程的取消是 协作 的。一段协程代码必须协作才能被取消。 所有 kotlinx.coroutines 中的挂起函数都是 可被取消的 。它们检查协程的取消， 并在取消时抛出 CancellationException。
     * 然而，如果协程正在执行计算任务，并且没有检查取消的话，那么它是不能被取消的
     * 运行以下示例代码，并且我们可以看到它连续打印出了“I'm sleeping”，甚至在调用取消后， 作业仍然执行了五次循环迭代并运行到了它结束为止。
     */
    private fun cancelIsCooperative() {
        runBlocking {
            val startTime = System.currentTimeMillis()
            val job = launch(Dispatchers.Default) {
                var nextPrintTime = startTime
                var i = 0
                while (i < 5) {
                    if (System.currentTimeMillis() >= nextPrintTime) {
                        println("Job:I'm sleeping ${i++}")
                        nextPrintTime += 500
                    }
                }

            }
            // 等待一段时间
            delay(1300)
            println("I'm tired of waiting")
            job.cancelAndJoin()
            println("main,Now I can quit")
        }

    }

    /**
     * 使计算代码可取消
     *
     */
    private fun ensureActive() {
        runBlocking {
            // sampleStart
            val startTime = System.currentTimeMillis()
            val job = launch(Dispatchers.Default) {
                var nextPrintTime = startTime
                var i = 0
                // while (true) { // 可以被取消的计算循环
                while (isActive) { // 可以被取消的计算循环
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
            // sampleEnd
        }
    }

    /**
     * 在前一个例子中任何尝试在 finally 块中调用挂起函数的行为都会抛出 CancellationException，因为这里持续运行的代码是可以被取消的。
     * 在真实的案例中，当你需要挂起一个被取消的协程，你可以将相应的代码包装在 withContext(NonCancellable) {……} 中，并使用 withContext 函数以及 NonCancellable 上下文
     *
     */
    private fun nonCancellable() {
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


    private fun doInFinally() {
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

    private fun cancelJob() {
        runBlocking {
            val job = launch {
                repeat(10000) {
                    println("Job: I am sleeping $it")
                    delay(500)
                }
            }
            delay(1300)
            println("main: I'm tired of waiting!")
            job.cancel()
            job.join()
            // 上面 job.cancel() 和 job.join() 可用 job.cancelAndJoin() 替代
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
                for (i in 0 until 100_100) {
                    Thread.sleep(2000)
                    println(i)
                }

            }
        }
    }


    /**
     * 输出:
     * Hello
     * world
     * Done
     */
    private fun jobJoin() {
        mainScope.launch {
            val job = launch {
                delay(5000)
                println("world")
            }
            println("Hello")
            job.join() // 等待子协程执行完成
            println("Done ")
        }
        // viewModel.testJob()
    }

    /**
     *
     *  runBlocking:会阻塞了当前等待的线程，
     *  coroutineScope： 只是暂停，
     *  释放了用于其他用途的底层线程。因为这个区别，runBlocking 是一个常规函数，coroutescope 是一个挂起函数。
     *  输出:
     *  Hello
     *  world 1:main
     *  World 2 thread:main
     *  Done
     */
    private fun coroutineScope() {
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


    /**
     * 先输出 hello 后输出 world
     */

    private fun firstCoroutineExample() {
        runBlocking {
            // 启动一个新的协程
            launch {
                // delay(1000)
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


    private suspend fun doWorld() {
        delay(1000)
        println("World")
    }

}

