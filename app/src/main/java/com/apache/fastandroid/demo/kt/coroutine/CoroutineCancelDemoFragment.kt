package com.apache.fastandroid.demo.kt.coroutine

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentCoroutineCancelBinding
import com.blankj.utilcode.util.ToastUtils
import com.tesla.framework.ui.fragment.BaseBindingFragment
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield

/**
 * Created by Jerry on 2021/10/28.
 * 协程取消
 * 参考：https://medium.com/androiddevelopers/cancellation-in-coroutines-aa6b90163629
 */
class CoroutineCancelDemoFragment:BaseBindingFragment<FragmentCoroutineCancelBinding>(FragmentCoroutineCancelBinding::inflate) {
    companion object{
        private const val TAG = "CoroutineCancelDemoFragment"
    }

    private var job: Job? = null

    private val scope = CoroutineScope(Job() + Dispatchers.Main)


    private val mainScope = MainScope()

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.btnEnsureCoroutineActive.setOnClickListener {
            ensureCoroutineActivive()
            startCoroutine()
        }
        mBinding.btnCancelCoroutine.setOnClickListener {
            job?.cancel()
        }

        mBinding.btnCancelAllCoroutine.setOnClickListener {
            scope.cancel()
        }

        mBinding.btnCancelJob1.setOnClickListener {
            job1?.cancel()
        }
        mBinding.btnCancelNormal.setOnClickListener {
            normalCancel()
        }
        mBinding.btnCancelImmediately.setOnClickListener {
            cancelRightNow()
        }
        mBinding.btnDeferredCancel.setOnClickListener {
            defferedCancel()
        }

        mBinding.btnHandleCancel.setOnClickListener {
            handleCoroutineCancel()
        }
        mBinding.btnHandleByCatch.setOnClickListener {
            handleByCatch()
        }
        mBinding.btnCancelCallSuspend.setOnClickListener {
            callSuspendInCancel()
        }
    }

    private fun callSuspendInCancel() {
        runBlocking<Unit> {
            val job = launch(Dispatchers.Default) {
                try {
                    work()
                } catch (e: CancellationException) {
                    println("Work cancelled!")
                } finally {
                    //协程被取消时必须在NonCancellable CoroutineContext中才能调用挂起函数
                    withContext(NonCancellable){
                        delay(1000)
                        println("Clean up!")
                    }

                }
            }
            delay(1000L)
            println("Cancel!")
            job.cancel()
            println("Done!")
        }
    }

    suspend fun work(){
        val startTime = System.currentTimeMillis()
        var nextPrintTime = startTime
        var i = 0
        while (i < 5) {
            yield()
            // print a message twice a second
            if (System.currentTimeMillis() >= nextPrintTime) {
                println("Hello ${i++}")
                nextPrintTime += 500L
            }
        }
    }

    suspend fun work2(){


    }

    private fun handleByCatch(){
        runBlocking<Unit> {
            val job = launch(Dispatchers.Default) {
                try {
                    work()
                } catch (e: CancellationException) {
                    println("Work cancelled!")
                } finally {
                    //这里调用 delay 不会执行到下面的 Clean up，需要在NonCancellable CoroutineContext中调用delay 整个挂起函数
//                    delay(1000)
                    println("Clean up!")
                }
            }
            delay(1000L)
            println("Cancel!")
            job.cancel()
            println("Done!")
        }
    }

    private fun handleCoroutineCancel() {
        runBlocking<Unit> {
            val startTime = System.currentTimeMillis()
            val job = launch (Dispatchers.Default) {
                var nextPrintTime = startTime
                var i = 0
                while (i < 5 && isActive) {
                    // print a message twice a second
                    if (System.currentTimeMillis() >= nextPrintTime) {
                        println("Hello ${i++}")
                        nextPrintTime += 500L
                    }
                }
                // the coroutine work is completed so we can cleanup
                println("Clean up!")

            }
            delay(1000L)
            println("Cancel!")
            job.cancel()
            println("Done!")
        }
    }

    private fun defferedCancel() {
        scope.launch {
            val deferred = async {
                delay(1000)
                "hello"
            }
            deferred.cancel()
            val result =  deferred.await()
            println("result:$result")
        }
    }

    private var job1:Job ?= null
    private var job2:Job ?= null

    /**
     *
     */
    private fun normalCancel(){
        runBlocking {
            val startTime = System.currentTimeMillis()
            val job = launch (Dispatchers.Default) {
                var nextPrintTime = startTime
                var i = 0
                /**
                 * 不加 isActive 的条件会输出 Hello 0,1,2 Cancel! Done! Hello 3 Hello 4
                 * 加 isActive 的条件会输出 Hello 0,1,2
                 */
                while (i < 5 ) {
                    // print a message twice a second
                    if (System.currentTimeMillis() >= nextPrintTime) {
                        println("Hello ${i++}")
                        nextPrintTime += 500L
                    }
                }
            }
            delay(1100L)
            println("Cancel!")
            job.cancel()
            println("Done!")
        }
    }

    private fun cancelRightNow(){
        runBlocking {
            val startTime = System.currentTimeMillis()
            val job = launch (Dispatchers.Default) {
                var nextPrintTime = startTime
                var i = 0
                /**
                 * 不加 isActive 的条件会输出 Hello 0,1,2 Cancel! Done! Hello 3 Hello 4
                 * 加 isActive 的条件会输出 Hello 0,1,2
                 */
                while (i < 5 && isActive) {
                    // print a message twice a second
                    if (System.currentTimeMillis() >= nextPrintTime) {
                        println("Hello ${i++}")
                        nextPrintTime += 500L
                    }
                }
            }
            delay(1100L)
            println("Cancel!")
            job.cancel()
            println("Done!")
        }
    }

    private fun startCoroutine(){
        job1 = scope.launch {
            delay(5000)
            println("job1 run thread:${Thread.currentThread().name}")
        }
        job2 = scope.launch {
            delay(5000)
            println("job2 run thread:${Thread.currentThread().name}")
        }

    }

    private fun ensureCoroutineActivive() {
        try {
            job = mainScope.launch {
                val users = arrayOf("aaa", "bbb")
                for (user in users){
                    ensureActive()
                    withContext(Dispatchers.IO){
                        try {
                            println("user:$user")
                        }catch (ex:Exception){
                            ex.printStackTrace()
                        }
                        delay(2000)
                    }

                }
            }
        }catch (ex:Exception){
            ex.printStackTrace()
            ToastUtils.showShort("cancel job:${ex.message}")
        }

    }


}

