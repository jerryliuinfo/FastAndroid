package com.apache.fastandroid.jetpack.flow.share

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.apache.fastandroid.databinding.FragmentFlowTimerBinding
import com.apache.fastandroid.databinding.FragmentStateFlowBasicBinding
import com.apache.fastandroid.jetpack.flow.vm.FlowViewModel
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.ui.fragment.BaseBindingFragment
import kotlinx.coroutines.launch

/**
 * Created by Jerry on 2023/12/10.
 */
class StateFlowBasicDemoFragment:BaseBindingFragment<FragmentStateFlowBasicBinding>(FragmentStateFlowBasicBinding::inflate) {

    private val mViewModel: FlowViewModel by viewModels()

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)


        lifecycleScope.launchWhenStarted {
            mViewModel.stateFlow.collect {
                Logger.d("stateFlow collect it:$it")
                mBinding.tvResult.text = it.toString()
            }
        }


        mBinding.btnFlowTimer.setOnClickListener {
            mViewModel.startCount()
        }

    }
}