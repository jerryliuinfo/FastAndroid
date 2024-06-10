package com.apache.fastandroid.jetpack.flow

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentCoroutineFlowBinding
import com.tesla.framework.ui.fragment.BaseBindingFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.toSet
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.system.measureTimeMillis

/**
 * Created by Jerry on 2021/10/28.
 * Flow
 * 参考：https://book.kotlincn.net/text/flow.html
 */
class FlowDemoFragment :
    BaseBindingFragment<FragmentCoroutineFlowBinding>(FragmentCoroutineFlowBinding::inflate) {
    companion object {
        private const val TAG = "FlowBasicUsageFragment"

    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.btnSequence.setOnClickListener {
            testSequence()
        }
        mBinding.btnSuspend.setOnClickListener {
            testSuspend()
        }
        mBinding.btnFlow.setOnClickListener {
            testFlow()
        }
        mBinding.btnFlowBlock.setOnClickListener {
            testFlowBlock()
        }
        mBinding.btnFlowIsCold.setOnClickListener {
            testFlowIsCold()
        }
        mBinding.btnFlowCancel.setOnClickListener {
            cancelFlow()
        }
        mBinding.btnFlowBuilder.setOnClickListener {
            asFlow()
        }
        mBinding.btnFlowMap.setOnClickListener {
            flowMap()
        }
        mBinding.btnFlowTransform.setOnClickListener {
            flowTransform()
        }
        mBinding.btnFlowTake.setOnClickListener {
            flowTake()
        }
        mBinding.btnEndFlow.setOnClickListener {
            endFlow()
        }
        mBinding.btnFlowIsContinuous.setOnClickListener {
            flowIsContinuous()
        }
        mBinding.btnFlowContext.setOnClickListener {
            flowContext()
        }
        mBinding.btnWitchContextError.setOnClickListener {
            withContextEmitError()
        }
        mBinding.btnFlowOn.setOnClickListener {
            flowOn()
        }
        mBinding.btnFlowBuffer.setOnClickListener {
            flowBuffer()
        }
        mBinding.btnFlowConflate.setOnClickListener {
            flowConflate()
        }
        mBinding.btnFlowCollectLatest.setOnClickListener {
            collectLatest()
        }
        mBinding.btnFlowZip.setOnClickListener {
            flowZip()
        }
        mBinding.btnFlowCombine.setOnClickListener {
            flowCombine()
        }
        mBinding.btnFlatMapConcat.setOnClickListener {
            flatMapOnContact()
        }
        mBinding.btnFlatMapMerge.setOnClickListener {
            flatMapMerge()
        }
        mBinding.btnFlowException.setOnClickListener {
            flowException()
        }
        mBinding.btnFlowCatchAll.setOnClickListener {
            catchAllException()
        }
        mBinding.btnExceptionTransparency.setOnClickListener {
            exceptionTransparency()
        }
        mBinding.btnTransparencyCatch.setOnClickListener {
            transparencyCatch()
        }
        mBinding.btnDeclareCatch.setOnClickListener {
            declareCatchException()
        }
        mBinding.btnCommandFinally.setOnClickListener {
            commandFinally()
        }
        mBinding.btnDeclareFinally.setOnClickListener {
            declareFinally()
        }
        mBinding.btnOnCompletionCatch.setOnClickListener {
            onCompletionCatch()
        }
        mBinding.btnLaunchFlow.setOnClickListener {
            launchFlow()
        }
    }

    fun events():Flow<Int> = (1..3).asFlow().onEach { delay(100) }
    private fun launchFlow() {
        runBlocking {
            events().onEach { event ->
                println("Event: $event")
            }.collect {
                println("Collected $it")
            }
            println("Done")
        }
    }

    /**
     * onCompletion 操作符与 catch 不同，它不处理异常。
     * 我们可以看到前面的示例代码，异常仍然流向下游。它将被提供给后面的
     * onCompletion 操作符，并可以由 catch 操作符处理。
     */
    private fun onCompletionCatch() {
        runBlocking {
            simpleFlowThrowException()
                .onCompletion { cause ->
                    println("onCompletion cause: $cause")
                }
                .catch { cause ->
                    println("Caught excepition: ${cause.message}")
                }
                .collect {
                    println("Collect $it")
                }
        }
    }

    /**
     * onCompletion 的主要优点是其 lambda 表达式的可空参数 Throwable 可以用于确定流收集是正常完成还是有异常发生。在下面的示例中 simple 流在发射数字 1 之后抛出了一个异常：
     */
    private fun declareFinally() {
        runBlocking {
            simpleFlow()
                .onCompletion { cause ->
                    if (cause != null){
                        println("flow completed exception:$cause")
                    }else{
                        println("flow completed successfully cause:$cause")
                    }
                }
                .collect {
                    println(it)
                }
        }
    }

    /**
     * 当流收集完成时（普通情况或异常情况），它可能需要执行一个动作。
     * 除了 try/catch 之外，收集器还能使用 finally 块在 collect 完成时执行一个动作。
     *
     */
    private fun commandFinally() {
        runBlocking {
            try {
                simpleFlow().collect {
                    println(it)
                }
            }finally {
                println("Done")
            }

        }
    }

    /**
     * 我们可以将 catch 操作符的声明性与处理所有异常的期望相结合，将 collect
     * 操作符的代码块移动到 onEach 中，并将其放到 catch 操作符之前。
     * 收集该流必须由调用无参的 collect() 来触发：
     */
    private fun declareCatchException() {
        runBlocking {
            simpleFlow().onEach { value ->
                check(value <= 1){
                    "Collected $value"
                }
                println(value)
            }.catch { e->
                println("Caught $e")
            }.collect{
                println("collect : $it")
            }
        }
    }

    /**
     * catch 过渡操作符遵循异常透明性，仅捕获上游异常（catch 操作符上游的异常，
     * 但是它下面的不是）。 如果 collect { ... } 块（位于 catch 之下）抛出一个异常，
     * 那么异常会逃逸：
     */
    private fun transparencyCatch() {
        runBlocking {
            simpleFlow()
                .catch { e ->
                    println("Caught $e")
                 }
                .collect { value ->
                    check(value <= 1){
                        "Collected $value"
                    }
                    println(value)
                }
        }
    }

    /**
     * 流必须对异常透明，即在 flow { ... } 构建器内部的
     * try/catch 块中发射值是违反异常透明性的。这样可以保证收集器抛出的一个异常能被像先前示例中那样的
     * try/catch 块捕获。
     * 即使我们不再在代码的外层使用 try/catch，示例的输出也是相同的。


     */
    private fun exceptionTransparency() {
        runBlocking {
            simpleFlowThrowException()
                .catch { e ->
                    //反射一个异常
                    emit("Caught $e")
                }
                .collect { value ->
                    println(value)
                }
        }
    }

    private fun catchAllException() {
        runBlocking {
            try {
                simpleFlowThrowException().collect {
                    println(it)
                }
            }catch (e:Throwable){
                println("Caught: $e")
            }
        }
    }

    private fun flowException() {
        runBlocking {
            try {
                simpleFlow().collect { value ->
                    println(value)
                    check(value <= 1){
                        "Collected $value, throw exception"
                    }
                }
            }catch (e:Throwable){
                println("Caught: $e")
            }

        }
    }

    private fun flatMapMerge() {
        runBlocking {
            val startTime = System.currentTimeMillis()
            (1..3).asFlow().onEach {
                delay(100)
            }.flatMapMerge {
                requestFlow(it)
            }.collect { value ->
                println("$value at ${System.currentTimeMillis() - startTime} ms from start")
            }
        }
    }

    private fun requestFlow(i:Int):Flow<String> = flow {
        emit("$i: First")
        delay(500)
        emit("$i: Second")
    }

    /**
     * 连接模式由 flatMapConcat 与 flattenConcat 操作符实现。它们是相应序列操作符最相近的类似物。它们在等待内部流完成之后开始收集下一个值
     */
    private fun flatMapOnContact() {
        runBlocking {
            val startTime = System.currentTimeMillis()
            (1..3).asFlow().onEach {
                delay(100)
            }.flatMapConcat {
                requestFlow(it)
            }.collect { value ->
                println("$value at ${System.currentTimeMillis() - startTime} ms from start")
            }
        }
    }

    private fun flowCombine() {
        var numbs = intRange.asFlow().onEach { delay(300) }
        var strs = flowOf("one", "two", "three").onEach { delay(400) }
        runBlocking {
            val notUseCombineTime = measureTimeMillis {
                var startTime = System.currentTimeMillis()
                numbs.zip(strs) { a, b ->
                    "$a -> $b"
                }.collect { value ->
                    println("$value at ${System.currentTimeMillis() - startTime} ms from start")
                }
            }
            println("notUseCombineTime in ${notUseCombineTime} ms")

            val useCombineTime = measureTimeMillis {
                var startTime = System.currentTimeMillis()
                numbs.combine(strs) { a, b ->
                    "$a -> $b"
                }.collect { value ->
                    println("$value at ${System.currentTimeMillis() - startTime} ms from start")
                }
            }
            println("useCombineTime in ${useCombineTime} ms")
        }
    }

    private fun flowZip() {
        runBlocking {
            val time = measureTimeMillis {
                var numbs = intRange.asFlow()
                var strs = flowOf("one", "two", "three")
                var startTime = System.currentTimeMillis()
                numbs.zip(strs) { a, b ->
                    "$a -> $b"
                }.collect { value ->
                    println("$value at ${System.currentTimeMillis() - startTime} ms from start")
                }
            }
            println("Collected1 in ${time} ms")


        }

    }

    private fun collectLatest() {
        runBlocking {
            val time = measureTimeMillis {
                simpleFlow()
                    .collectLatest {
                        println("Collecting $it")
                        delay(300)
                        println("Done $it")
                    }
            }
            println("Collected in $time ms")

        }
    }

    /**
     * 当流代表部分操作结果或操作状态更新时，可能没有必要处理每个值，而是只处理最新的那个。
     * 在本示例中，当收集器处理它们太慢的时候， conflate 操作符可以用于跳过中间值
     */
    private fun flowConflate() {
        runBlocking {
            val time = measureTimeMillis {
                simpleFlow()
                    .conflate()
                    .collect {
                        delay(300)
                        println(it)
                    }
            }
            println("Collected in $time ms")
        }

    }

    private fun flowBuffer() {
        //不使用 buffer
        runBlocking {
            var time = measureTimeMillis {
                simpleFlow()
                    .collect {
                        delay(300)
                        println(it)
                    }
            }
            println("Collected not user buffer in $time ms")

            time = measureTimeMillis {
                simpleFlow()
                    .buffer()
                    .collect {
                        delay(300)
                        println(it)
                    }
            }
            println("Collected  user buffer in $time ms")
        }

    }

    private fun flowOn() {
        runBlocking {
            simpleFlowOn().collect {
                println(it)
            }
        }
    }

    /**
     * 长时间运行的消耗 CPU 的代码也许需要在 Dispatchers.Default 上下文中执行，并且更新 UI 的代码也许需要在 Dispatchers.Main 中执行。
     * 通常，withContext 用于在 Kotlin 协程中改变代码的上下文，但是 flow {...} 构建器中的代码必须遵循上下文保存属性，
     * 并且不允许从其他上下文中发射（emit）。例如下面代码将会抛出异常
     * Exception in thread "main" java.lang.IllegalStateException: Flow invariant is violated:
     */
    private fun withContextEmitError() {
        runBlocking {
            simpleFlowWithContextError().collect {
                log("Collected $it")
            }
        }
    }

    /**
     * 流的收集总是在调用协程的上下文中发生。例如，如果有一个流 simple，
     * 然后以下代码在它的编写者指定的上下文中运行，而无论流 simple 的实现细节如何
     * 由于 simple().collect 是在主线程调用的，那么 simple 的流主体也是在主线程调用的。
     * 这是快速运行或异步代码的理想默认形式，它不关心执行的上下文并且不会阻塞调用者。
     */
    private fun flowContext() {
        runBlocking {
            simpleFlow().collect {
                log("collected $it")
            }
        }

    }

    /**
     * 流的每次单独收集都是按顺序执行的，除非进行特殊操作的操作符使用多个流。
     * 该收集过程直接在协程中运行，该协程调用末端操作符。
     * 默认情况下不启动新协程。 从上游到下游每个过渡操作符都会处理每个发射出的值然后再交给末端操作符。
     */
    private fun flowIsContinuous() {
        runBlocking {
            intRange.asFlow().filter {
                println("Filter $it")
                it % 2 == 0
            }.map {
                println("Map $it")
                "String $it"
            }.collect {
                println("Collect $it")
            }
        }

    }


    val intRange = IntRange(1, 5)

    private fun endFlow() {
        runBlocking {
            val first = intRange.asFlow().first()
            val list = intRange.asFlow().toList()
            val set = intRange.asFlow().toSet()
            val sum = intRange.asFlow().map {
                it * it
            }.reduce { accumulator, value ->
                accumulator + value
            }
            println("first:$first \n, list:$list\n, set:$set\n, sum:$sum")
        }
    }

    /**
     * 限长过渡操作符（例如 take）在流触及相应限制的时候会将它的执行取消。
     * 协程中的取消操作总是通过抛出异常来执行，这样所有的资源管理函数（如 try {...} finally {...} 块）会在取消的情况下正常运行：
     */
    private fun flowTake() {
        runBlocking {
            numbers().take(2).collect {
                println(it)
            }
        }
    }

    fun numbers(): Flow<Int> = flow {
        try {
            emit(1)
            emit(2)
            println("This line will not execute")
            emit(3)
        } finally {
            println("Finally in numbers")
        }
    }


    private fun flowTransform() {
        runBlocking {
            (1..3).asFlow()
                .transform { request ->
                    emit("Making request:$request")
                    emit(performRequest(request))
                }
                .collect { response ->
                    println(response)
                }
        }

    }

    private fun flowMap() {
        runBlocking {
            (1..3).asFlow().map { request ->
                performRequest(request)
            }.collect { response ->
                println(response)
            }
        }
    }

    private suspend fun performRequest(request: Int): String {
        delay(1000)
        return "response:$request"
    }


    private fun asFlow() {
        runBlocking {
            (1..3).asFlow().collect {
                println(it)
            }
        }
    }

    /**
     * 流采用与协程同样的协作取消。像往常一样，流的收集可以在当流在一个可取消的挂起函数（例如 delay）中挂起的时候取消
     */
    private fun cancelFlow() {
        runBlocking {
            withTimeoutOrNull(300) {
                simpleFlow().collect {
                    println(it)
                }
            }
            println("Done")
        }
    }

    /**
     * Flow 是一种类似于序列的冷流 — 这段 flow 构建器中的代码直到流被收集的时候才运行
     */
    private fun testFlowIsCold() {
        runBlocking {
            println("call simple function")
            //这个时候 flow  中的代码还不会被执行
            val flow = simpleFlow()
            println("calling collect")
            flow.collect {
                println(it)
            }
            println("calling collect again")
            flow.collect {
                println(it)
            }

        }

    }

    /**
     * 名为 flow 的 Flow 类型构建器函数。
    flow { ... } 构建块中的代码可以挂起。
    函数 simple 不再标有 suspend 修饰符。
    流使用 emit 函数 发射 值。
    流使用 collect 函数 收集 值。
     */
    private fun testFlow() {
        runBlocking {
            launch {
                for (k in 1..3) {
                    println("I am not blocking ${k}")
                    delay(200)
                }
            }

            simpleFlow().collect {
                println(it)
            }
        }
    }

    /**
     * 会先执行 launch启动协程完成后才会打印 flow 的collect 结果
     */
    private fun testFlowBlock() {
        runBlocking {
            launch {
                for (k in 1..3) {
                    println("I am not blocking ${k}, thread:${Thread.currentThread().name}")
                    Thread.sleep(200)
                }
            }

            simpleFlow().collect {
                println("value:$it, thread:${Thread.currentThread().name}")
            }
        }
    }

    private fun testSuspend() {
        runBlocking {
            simple2().forEach {
                println(it)
            }
        }
    }


    fun simpleFlow(): Flow<Int> = flow {
        println("flow started")
        for (i in 1..3) {
            delay(100)
            println("Emitting ${i}")
            emit(i)
        }
    }

    fun simpleFlowWithContextError(): Flow<Int> = flow {
        println("flow started")
        withContext(Dispatchers.Default) {
            for (i in 1..3) {
                delay(100)
                println("Emitting ${i}")
                emit(i)
            }
        }

    }
    fun simpleFlowThrowException(): Flow<String> = flow {
        for (i in 1..3){
            println("Emitting $i")
            emit(i)
        }

    }.map { value ->
        check(value <= 1){
            "Crashed on $value"
        }
        "string $value"
    }

    fun simpleFlowOn(): Flow<Int> = flow {
        println("flow started")
        for (i in 1..3) {
            delay(100)
            println("Emitting ${i}")
            emit(i)
        }
    }.flowOn(Dispatchers.Default)

    suspend fun simple2(): List<Int> {
        delay(1000)
        return listOf(1, 2, 3)
    }

    fun simpleSequence(): Sequence<Int> = sequence {
        for (i in 1..3) {
            Thread.sleep(500)
            yield(i)
        }
    }

    private fun testSequence() {
        simpleSequence().forEach {
            println(it)
        }
    }

    private fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")

}

