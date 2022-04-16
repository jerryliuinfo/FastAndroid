package com.apache.fastandroid.demo.kt.coroutine

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentCoroutineCancelTimeoutBinding
import com.apache.fastandroid.databinding.FragmentCoroutineContextDispatcherBinding
import com.apache.fastandroid.databinding.FragmentCoroutineSuspendBinding
import com.tesla.framework.ui.fragment.BaseVBFragment
import kotlinx.coroutines.*
import java.lang.ArithmeticException
import kotlin.system.measureTimeMillis

/**
 * Created by Jerry on 2021/10/28.
 * 协程上下文与调度器
 * 参考：https://book.kotlincn.net/text/coroutine-context-and-dispatchers.html
 */
class CoroutineContextDispatcherDemoFragment :
    BaseVBFragment<FragmentCoroutineContextDispatcherBinding>(FragmentCoroutineContextDispatcherBinding::inflate) {
    companion object {
        private const val TAG = "CoroutineCancelTimeoutDemoFragment"
    }


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.btnDispatchersThreads.setOnClickListener {
            dispatchThreads()
        }

        mBinding.btnCoroutineNameing.setOnClickListener {
            coroutineNaming()
        }
        mBinding.btnAssembleContextElement.setOnClickListener {
             assembleContextElement()
        }

        mBinding.btnActionScope.setOnClickListener {
            actionScope()
        }
        mBinding.btnThreadLocalData.setOnClickListener {
            threadLocalData()
        }

    }

    private val threadLocal = ThreadLocal<String?>()
    private fun threadLocalData() {
        runBlocking {
            threadLocal.set("main")
            println("Pre-main, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")
            val job = launch(Dispatchers.Default + threadLocal.asContextElement(value = "launch")) {
                println("Launch start, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")
                yield()
                println("After yield, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")
            }
            job.join()
            println("Post-main, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")

        }


    }

    private val mainScope = MainScope()

    private fun actionScope() {
            repeat(10){ i ->
                mainScope.launch {
                    delay((i +1) * 500L)
                    println("coroutine ${i} is done")
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mainScope.cancel()
    }

    private fun assembleContextElement() {
        runBlocking {
            launch(Dispatchers.Default + CoroutineName("test")) {
                println("I am working in thread:${Thread.currentThread().name}")
            }
        }
    }

    private fun coroutineNaming() {
        runBlocking(CoroutineName("main")) {
            println("start main coroutine")
            val v1 = async(CoroutineName("v1Coroutine")) {
                delay(500)
                println("computing v1")
                252
            }
            val v2 = async(CoroutineName("v2Coroutine")) {
                delay(1000)
                println("computing v2")
                6
            }
            println("the answer for v1/v2: ${v1.await()} / ${v2.await()}")
        }

    }

    /**
     * 当调用 launch { …… } 时不传参数，它从启动了它的 CoroutineScope 中承袭了上下文（以及调度器）。
     * 在这个案例中，它从 main 线程中的 runBlocking 主协程承袭了上下文。
     * newSingleThreadContext 为协程的运行启动了一个线程。 一个专用的线程是一种非常昂贵的资源。 在真实的应用程序中两者都必须被释放，当不再需要的时候，使用 close 函数，或存储在一个顶层变量中使它在整个应用程序中被重用。


     */
    private fun dispatchThreads() {
        runBlocking {
            launch { // 运行在父协程的上下文中，即 runBlocking 主协程
                println("main runBlocking      : I'm working in thread ${Thread.currentThread().name}")
            }
            launch(Dispatchers.Unconfined) { // 不受限的——将工作在主线程中
                println("Unconfined            : I'm working in thread ${Thread.currentThread().name}")
            }
            launch(Dispatchers.Default) { // 将会获取默认调度器
                println("Default               : I'm working in thread ${Thread.currentThread().name}")
            }
            launch(newSingleThreadContext("MyOwnThread")) { // 将使它获得一个新的线程
                println("newSingleThreadContext: I'm working in thread ${Thread.currentThread().name}")
            }
        }
    }


}

