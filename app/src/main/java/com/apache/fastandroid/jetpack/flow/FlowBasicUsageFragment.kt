package com.apache.fastandroid.jetpack.flow

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentFlowBasicUsageBinding
import com.tesla.framework.ui.fragment.BaseVBFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * Created by Jerry on 2022/5/3.
 * https://blog.mindorks.com/what-is-flow-in-kotlin-and-how-to-use-it-in-android-project
 */
class FlowBasicUsageFragment:BaseVBFragment<FragmentFlowBasicUsageBinding>(FragmentFlowBasicUsageBinding::inflate) {

    private val mainScope = MainScope()

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)


        mBinding.btnFlowBasic.setOnClickListener {
            flowUsage()
        }

        mBinding.btnFlowOf.setOnClickListener {
            flowOfUsage()
        }

        mBinding.btnAsFlow.setOnClickListener {
            asFlowUsage()
        }
        mBinding.btnChannelFlow.setOnClickListener {
            channelFlowUsage()
        }
        mBinding.btnZip.setOnClickListener {
            zipUsage()
        }
        mBinding.btnHandleException.setOnClickListener {
            handleFlowException()
        }
        mBinding.btnCatchException.setOnClickListener {
            handleFlowCatchException()
        }

        mBinding.btnExceptionAllFailure.setOnClickListener {
            exceptionAllFailure()
        }
        mBinding.btnExceptionSingleFailure.setOnClickListener {
            exceptionSingleFailure()
        }

    }

    /**
     * 只要有一个失败就回调 catch，
     */
    private fun exceptionAllFailure() {
        mainScope.launch {
            flow1().zip(flow2()){ first, second ->
                "$first:$second"
            }.flowOn(Dispatchers.IO)
                .catch { e ->
                    e.printStackTrace()
                }.onCompletion {
                    println("exceptionAllFailure onCompletion")
                }
                .collect{
                    println("exceptionAllFailure collect")
                    println(it)
                }
        }
    }

    /**
     * 单个请求失败不影响另外一个请求
     */
    private fun exceptionSingleFailure() {
        mainScope.launch {
            flow1().catch {e->
                println("exceptionSingleFailure flow1 catch ")
                emitAll(flowOf("empty charactor"))
            }.zip(flow2()){ first, second ->
                "$first:$second"
            }.flowOn(Dispatchers.IO)
                .catch { e->
                    e.printStackTrace()
                    println("exceptionSingleFailure  global catch")
                }
                .onCompletion {
                    println("exceptionSingleFailure onCompletion")
                }
                .collect{
                    println(it)
                }
        }
    }

    private fun flow1():Flow<String>{
        return flowOf("a", "b","c","d").map {
            check(it != "c")
            it
        }
    }

    private fun flow2():Flow<Int>{
        return flowOf(1,2,3,4)
    }


    private fun handleFlowCatchException() {
        MainScope().launch {
            (1..4).asFlow().map {
                check(it != 3){
                    "Value:$it"
                }
            }.onCompletion {
                println("onCompletion -->")

            }.catch { e ->
                e.printStackTrace()
            }.collect{
                println("collect $it -->")
            }
        }

    }

    private fun handleFlowException() {
        MainScope().launch {
            (1..4).asFlow().map {
                check(it != 3){
                    "Value:$it"
                }
            }.onCompletion {
                println("onCompletion -->")

            }.collect{
                println("collect $it -->")
            }
        }

    }

    private fun zipUsage() {
        val flowOne = flowOf("a", "b","c","d")
        val flowTow = flowOf(1,2,3)
        mainScope.launch {
            flowOne.zip(flowTow){ first, sencond ->
                "$first:$sencond"
            }
                .flowOn(Dispatchers.IO)
                .collect{
                    println(it)
            }
        }
    }

    private fun channelFlowUsage() {
        val flow = channelFlow<Int> {
            (0..5).onEach {
                send(it)
            }
        }
        observeFlow(flow)
    }

    private fun asFlowUsage() {
        val flow = (1..3).asFlow().onEach {
            delay(300)
        }.flowOn(Dispatchers.IO)
        observeFlow(flow)

    }

    /**
     * 用于从给定的一组值创建流
     */
    private fun flowOfUsage() {
        val flow = flowOf(1,4,3,7,5)
            .onEach {
                delay(400)
            }
            .flowOn(Dispatchers.IO)
        observeFlow(flow)
    }

    private fun observeFlow(flow: Flow<Int>) {
        mainScope.launch {
            flow.collect{
                println("collect $it")
            }
        }
    }


    private fun flowUsage(){
        val flow = flow {
            println("Flow start --->")
            (0..5).forEach {
                delay(500)
                emit(it)
            }
        }.map {
            it * it
        }.flowOn(Dispatchers.Default)
        observeFlow(flow)
    }

}