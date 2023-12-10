package com.apache.fastandroid.jetpack.flow.stateflow

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.test.internal.util.LogUtil
import com.apache.fastandroid.databinding.FragmentFlowShareBinding
import com.apache.fastandroid.demo.kt.coroutine.CoroutineVewModel
import com.apache.fastandroid.jetpack.flow.vm.FlowShareDemoViewModel
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.ui.fragment.BaseBindingFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

/**
 * Created by Jerry on 2023/12/10.
 */
class FlowShareDemoFragment:BaseBindingFragment<FragmentFlowShareBinding>(FragmentFlowShareBinding::inflate) {

    private val mViewModel: FlowShareDemoViewModel by viewModels()


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.btnBasicFlow.setOnClickListener {
            flowBasicUsage()
        }

        mBinding.btnColdFlow.setOnClickListener {
            coldFlow()
        }

        mBinding.btnStateFlow.setOnClickListener {
           mViewModel.startCount()
        }
        stateFlowUsage()
    }

    private fun flowBasicUsage() {
        lifecycleScope.launchWhenStarted {
            flow {
                (0..10).forEach {
                    emit(it)
                }
            }.map {
                it * it
            }.collect {
                Logger.d("emit $it, thread:${Thread.currentThread().name}")
            }
        }
    }


    private fun stateFlowUsage() {
        //按 home 键回到桌面时，虽然没有继续打印了，但是发送端还是在继续发送的
        lifecycleScope.launchWhenStarted {
            mViewModel.stateFlow.collect {
                Logger.d("stateFlow collect it:$it")
                mBinding.tvResult.text = it.toString()
            }
        }
    }

    private fun coldFlow(){
        lifecycleScope.launchWhenStarted {
            mViewModel.coldFlow.collect {
                Logger.d("coldFlow collector1:$it, thread:${Thread.currentThread().name}")
            }
        }


    }
}