package com.apache.fastandroid.demo.kt.coroutine

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentCoroutineFlowBinding
import com.tesla.framework.ui.fragment.BaseVBFragment
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

/**
 * Created by Jerry on 2021/10/28.
 * Flow
 * 参考：https://book.kotlincn.net/text/flow.html
 */
class FlowDemoFragment:BaseVBFragment<FragmentCoroutineFlowBinding>(FragmentCoroutineFlowBinding::inflate) {
    companion object{
        private const val TAG = "FlowDemoFragment"

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
    }

    private fun asFlow() {
        runBlocking {
            (1..3).asFlow().collect {
                println(it)
            }
        }
    }

    private fun cancelFlow() {
        runBlocking {
            withTimeoutOrNull(300){
                simpleFlow().collect {
                    println(it)
                }
            }
            println("Done")
        }
    }

    private fun testFlowIsCold() {
        runBlocking {
            println("call simple function")
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
                for (k in 1..3){
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
                for (k in 1..3){
                    println("I am not blocking ${k}, thread:${Thread.currentThread().name}")
                    Thread.sleep(200)
                }
            }

            simpleFlow().collect {
                println("value:$it, thread:${Thread.currentThread().name}")
            }
        }
    }

    private fun testSuspend(){
        runBlocking {
            simple2().forEach {
                println(it)
            }
        }
    }


     fun simpleFlow(): Flow<Int> = flow{
         println("flow started")
       for (i in 1..3){
           delay(100)
           println("Emitting ${i}")
           emit(i)
       }
    }

    suspend fun simple2():List<Int>{
        delay(1000)
        return listOf(1,2,3)
    }

    fun simpleSequence():Sequence<Int> = sequence{
        for (i in 1..3){
            Thread.sleep(500)
            yield(i)
        }
    }

    private fun testSequence() {
        simpleSequence().forEach {
            println(it)
        }
    }


}

