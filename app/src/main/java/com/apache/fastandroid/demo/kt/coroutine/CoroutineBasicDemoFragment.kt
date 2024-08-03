package com.apache.fastandroid.demo.kt.coroutine

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import com.apache.fastandroid.databinding.FragmentCoroutineBasicBinding
import com.tesla.framework.ui.fragment.BaseBindingFragment
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.asContextElement
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull
import kotlinx.coroutines.yield
import kotlin.concurrent.thread
import kotlin.coroutines.EmptyCoroutineContext
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

        mBinding.btnAsyncStyleFunction.setOnClickListener {
            asyncStyleFunction()
        }

        mBinding.btnAsyncConcurrent.setOnClickListener {

            asyncConcurrent()
        }

        mBinding.btnAsyncConcurrent2.setOnClickListener {
            asyncConcurrent2()
        }

        mBinding.btnDispatcher.setOnClickListener {
            dispatcherUsage()
        }

        mBinding.btnCoroutineJob.setOnClickListener {
            coroutineJobUsage()
        }

        mBinding.btnChildCoroutine.setOnClickListener {
            childCoroutineUsage()
        }

        mBinding.btnWithContext.setOnClickListener {
            withContextUsage()
        }

        mBinding.btnParentCoroutineDuty.setOnClickListener {
            parentJobDutyUsage()
        }

        mBinding.btnCoroutineName.setOnClickListener {
            coroutineNameUsage()
        }

        mBinding.btnElementCombination.setOnClickListener {
            coroutineContextElementCombination()
        }

        mBinding.btnCoroutineScope.setOnClickListener {
            coroutineScopeUsage()
        }

        mBinding.btnCancelLaunch.setOnClickListener {
            cancelAndLaunch()
        }

        mBinding.btnThreadLocalData.setOnClickListener {
            threadLocalData()
        }
    }


    private val threadLocal = ThreadLocal<String?>()

    /**
     * 有时，能够将一些线程局部数据传递到协程与协程之间是很方便的。
     * 然而，由于它们不受任何特定线程的约束，如果手动完成，可能会导致出现样板代码。
     *
     * ThreadLocal， asContextElement 扩展函数在这里会充当救兵。它创建了额外的上下文元素，
     * 且保留给定 ThreadLocal 的值，并在每次协程切换其上下文时恢复它。
     *
     * 输出:
     * Pre-main, current thread: Thread[main @coroutine#1,5,main], thread local value: 'main'
     * Launch start, current thread: Thread[DefaultDispatcher-worker-1 @coroutine#2,5,main], thread local value: 'launch'
     * After yield, current thread: Thread[DefaultDispatcher-worker-2 @coroutine#2,5,main], thread local value: 'launch'
     * Post-main, current thread: Thread[main @coroutine#1,5,main], thread local value: 'main'
     *
     *
     */
    private fun threadLocalData() {
        mainScope.launch {
            // 声明线程局部变量
            threadLocal.set("main")
            println("Pre-main, current thread:${Thread.currentThread().name}, thread local value:${threadLocal.get()}")
            val job = launch(Dispatchers.Default + threadLocal.asContextElement(value = "launch")) {
                println("Launch start, current thread:${Thread.currentThread().name}, thread local value:${threadLocal.get()}")
                yield()
                println("After yield, current thread:${Thread.currentThread().name}, thread local value:${threadLocal.get()}")
            }
            job.join()
            println("Post-main, current thread:${Thread.currentThread().name}, thread local value:${threadLocal.get()}")

        }
    }

    private var mJob:Job ?= null

    private fun cancelAndLaunch() {
        mJob?.cancel()
        println("cancel")
        mJob = mainScope.launch {
            delay(100)
            println("launch")
        }
    }


    val mCoroutineScope = CoroutineScope(EmptyCoroutineContext)
    /**
     * 我们通过创建一个 CoroutineScope 实例来管理协程的生命周期，并使它与 activity 的生命周期相关联。CoroutineScope 可以通过 CoroutineScope() 创建或者通过MainScope() 工厂函数。
     * 前者创建了一个通用作用域，而后者为使用 Dispatchers.Main 作为默认调度器的 UI 应用程序 创建作用域：
     *
     * onDestroy 时 mainScope启动的协程会自动取消
     */
    private fun coroutineScopeUsage() {

        repeat(10){ i ->
            mainScope.launch {
                delay((i +1) * 500L)
                println("Coroutine $i is done")
            }
        }

    }

    /**
     *
     * 有时我们需要在协程上下文中定义多个元素。我们可以使用 + 操作符来实现。 比如说，我们可以显式指定一个调度器来启动协程并且同时显式指定一个命名：
     * 输出:
     * I'm working in thread:DefaultDispatcher-worker-3 没有输出 后面的 @test
     * I'm working in thread DefaultDispatcher-worker-1 @test#2
     *
     */
    private fun coroutineContextElementCombination() {
        runBlocking {
            launch(Dispatchers.Default + CoroutineName("testtest")) {
                println("I'm working in thread:${Thread.currentThread().name}")
            }
        }
    }

    /**
     * [main @main#1] Started main coroutine
     * [main @v1coroutine#2] Computing v1
     * [main @v2coroutine#3] Computing v2
     * [main @main#1] The answer for v1 * v2 = 13
     */
    private fun coroutineNameUsage() {
        runBlocking(CoroutineName("main")) {
            log("Started main coroutine")
            val v1 = async(CoroutineName("v1coroutine")) {
                delay(500)
                log("Computing v1")
                6
            }
            val v2 = async(CoroutineName("v2coroutine")) {
                delay(1000)
                log("Computing v2")
                7
            }
            log("The answer for v1 + v2 = ${v1.await() + v2.await()}")
        }
    }

    fun log(msg:String) = println("${Thread.currentThread().name} $msg")

    private fun parentJobDutyUsage() {
        runBlocking {
            val request = launch {
                repeat(3){ i ->
                    launch {
                        delay( (i + 1) * 200L)
                        println("Coroutine $i is done, thread:${Thread.currentThread().name}")
                    }
                }
                println("request:I'm done and i don't explicitly join my children that are still active, thread:${Thread.currentThread().name}")
            }
            //等待请求的完成，包括其所有子协程
            request.join()
            println("Now processing of the request is completed, thread:${Thread.currentThread().name}")

        }
    }

    /**
     * 分别按书序执行
     * fun1 begin thread:DefaultDispatcher-worker-3
     * execute fun2 begin thread:DefaultDispatcher-worker-2
     * execute fun2 end
     * execute fun1 end
     * execute main end
     */
    private fun withContextUsage() {
        mainScope.launch {
            function1()
            println("execute main end")
        }
    }

    private suspend fun function1() = withContext(Dispatchers.IO){
        println("execute fun1 begin thread:${Thread.currentThread().name}")
        delay(500)
        function2()
        println("execute fun1 end")

    }

    private suspend fun function2() = withContext(Dispatchers.IO){
        println("execute fun2 begin thread:${Thread.currentThread().name}")
        delay(1000)
        println("execute fun2 end")

    }


    /**
     * 当一个协程被其它协程在 CoroutineScope 中启动的时候， 它将通过 CoroutineScope.coroutineContext 来承袭上下文，并且这个新协程的 Job 将会成为父协程作业的 子 作业。当一个父协程被取消的时候，所有它的子协程也会被递归的取消。
     *
     * 但有以下两种例外:
     * 1.当启动协程时显式指定不同的作用域（例如 GlobalScope.launch）时，它不会从父作用域继承作业。
     * 2.当一个不同的 Job 对象作为新协程的上下文传递时(如下面的示例所示) ，它将覆盖父范围的 Job。
     *
     */
    private fun childCoroutineUsage() {
        runBlocking {
            // 启动一个协程来处理某种传入请求（request）
            val request = launch {
                //使用一个新的 job 对象作为新协程的上下文
                launch(Job()){
                    println("Job1: I run in my own job and execute independently")
                    delay(1000)
                    println("job1: I am not affected by cancellation of the request")
                }
                //另一个新协程继承了父协程的上下文
                launch(){
                    delay(100)
                    println("job2: I am a child of the request coroutine")
                    delay(1000)
                    println("job2: I will not execute this line if my parent request is cancelled")
                }
            }
            delay(500)
            request.cancel()
            println("main: Who has survived request cancellation?")
            delay(1000) // 主线程延迟一秒钟来看看发生了什么
        }
    }


    /**
     * 输出
     * My job is "coroutine#1":BlockingCoroutine{Active}@6d311334
     *
     */
    private fun coroutineJobUsage() {
        runBlocking {
            println("My Job is ${coroutineContext[Job]}")
        }
    }

    /**
     * 当调用 launch { …… } 时不传参数，它从启动了它的 CoroutineScope 中承袭了上下文（以及调度器）。在这个案例中，它从 main 线程中的 runBlocking 主协程承袭了上下文。
     *
     * Dispatchers.Unconfined 是一个特殊的调度器且似乎也运行在 main 线程中，但实际上， 它是一种不同的机制，这会在后文中讲到。
     *
     * The default dispatcher is used when no other dispatcher is explicitly specified in the scope. It is represented by Dispatchers.Default and uses a shared background pool of threads.
     *
     * newSingleThreadContext 为协程的运行启动了一个线程。 一个专用的线程是一种非常昂贵的资源。 在真实的应用程序中两者都必须被释放，当不再需要的时候，使用 close 函数，或存储在一个顶层变量中使它在整个应用程序中被重用。
     */
    private fun dispatcherUsage() {
        runBlocking {
            launch {
                println("main runBlocking      : I'm working in thread ${Thread.currentThread().name}")
                delay(1000)
            }
            launch(Dispatchers.Unconfined) {
                println("Unconfined      : I'm working in thread ${Thread.currentThread().name}")
                delay(2000)
            }
            launch(Dispatchers.Default) {
                println("Default      : I'm working in thread ${Thread.currentThread().name}")
                delay(3000)
            }
            //将使它获得一个新的线程
            launch(newSingleThreadContext("MyOwnThread")) {
                println("newSingleThreadContext      : I'm working in thread ${Thread.currentThread().name}")
                delay(1000)
            }
        }
    }

    private fun asyncConcurrent2() {
        runBlocking {
            try {
                failedConcurrentSum()
            }catch (e:ArithmeticException){
                println("Computation failed with ArithmeticException")
            }
        }
    }


    /**
     * async 结构化并发
     */
    private fun asyncConcurrent() {
        runBlocking {
            val time = measureTimeMillis {
                val sum = concurrentSum()
                println("The answer is $sum")
            }
            println("Completed in $time ms")
        }

    }

    private suspend fun concurrentSum():Int = coroutineScope {
        val one = async { doSomething1() }
        val two = async { doSomething2() }
        return@coroutineScope one.await() + two.await()
    }


    /**
     * 如果其中一个子协程（即 two）失败，第一个 async 以及等待中的父协程都会被取消：
     * 输出
     * Second child throws an exception
     * First child was cancelled
     * Computation failed with ArithmeticException
     */
    private suspend fun failedConcurrentSum():Int = coroutineScope {
        val one = async<Int> {
            try {
                delay(Long.MAX_VALUE)
                42
            }finally {
                println("First child was canceled")
            }
        }
        val two = async<Int> {
            println("Second child throws an exception")
            throw ArithmeticException()

        }
        return@coroutineScope one.await() + two.await()
    }


    private fun asyncStyleFunction() {
        mainScope.launch {
            val time = measureTimeMillis {
                val one = somethingUsefulOneAsync()
                val two = somethingUsefulTwoAsync()
                runBlocking {
                    println("The answer is ${one.await() + two.await()}")
                }
            }
            println("Completed in $time")
        }

    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun somethingUsefulOneAsync() = GlobalScope.async {
        doSomething1()
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun somethingUsefulTwoAsync() = GlobalScope.async {
        doSomething2()
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

