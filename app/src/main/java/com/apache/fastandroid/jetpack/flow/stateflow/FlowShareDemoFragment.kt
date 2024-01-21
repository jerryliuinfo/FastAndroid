package com.apache.fastandroid.jetpack.flow.stateflow

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.apache.fastandroid.databinding.FragmentFlowShareBinding
import com.apache.fastandroid.jetpack.flow.share.FlowTimerDemoFragment
import com.apache.fastandroid.jetpack.flow.share.ShareFlowDemoFragment
import com.apache.fastandroid.jetpack.flow.share.StateFlowAdvanceDemoFragment
import com.apache.fastandroid.jetpack.flow.share.StateFlowBasicDemoFragment
import com.apache.fastandroid.jetpack.flow.vm.FlowViewModel
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.kt.launchFragment
import com.tesla.framework.ui.fragment.BaseBindingFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 * Created by Jerry on 2023/12/10.
 */
class FlowShareDemoFragment:BaseBindingFragment<FragmentFlowShareBinding>(FragmentFlowShareBinding::inflate) {

    private val mViewModel: FlowViewModel by viewModels()


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.btnBasicFlow.setOnClickListener {
            flowBasicUsage()
        }
        mBinding.btnFlowTimer.setOnClickListener {
            requireActivity().launchFragment<FlowTimerDemoFragment>()
        }
        mBinding.btnBackPressure.setOnClickListener {
            backPressureUsage()
        }

        mBinding.btnLifeCycle.setOnClickListener {
            lifeCycleUsage()
        }
        mBinding.btnStateFlow.setOnClickListener {
            requireActivity().launchFragment<StateFlowBasicDemoFragment>()
        }
        mBinding.btnStateFlowAdvance.setOnClickListener {
            requireActivity().launchFragment<StateFlowAdvanceDemoFragment>()
        }

        mBinding.btnShareFlow.setOnClickListener {
            requireActivity().launchFragment<ShareFlowDemoFragment>()
        }


    }

    private fun lifeCycleUsage() {
        lifecycleScope.launchWhenStarted {
            mViewModel.timeFlow.collect { time ->
                mBinding.tvResult.text = time.toString()
                Logger.d("lifeCycleUsage flow collect:$time, thread:${Thread.currentThread().name}")
            }
        }
    }

    private fun backPressureUsage() {
        lifecycleScope.launch {
            // mViewModel.timeFlow.collect { time ->
            //     Logger.d("flow collect:$time, thread:${Thread.currentThread().name}")
            //     mBinding.tvResult.text = time.toString()
            //     delay(3000)
            // }
            //下游处理速度慢于上游
            mViewModel.timeFlow.collectLatest { time ->
                Logger.d("flow collect:$time, thread:${Thread.currentThread().name}")
                mBinding.tvResult.text = time.toString()
                delay(3000)
            }
        }
    }

    private fun flowBasicUsage() {
        lifecycleScope.launchWhenStarted {
            flow {
                (1..10).forEach {
                    Logger.d("fllow emit:$it, thread:${Thread.currentThread().name}")
                    emit(it)
                    delay(1000)
                }
            }.map {
                it * it
            }.collect {
                Logger.d("emit $it, thread:${Thread.currentThread().name}")
                mBinding.tvResult.text = it.toString()
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


}