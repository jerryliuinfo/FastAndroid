package com.apache.fastandroid.jetpack.flow.stateflow

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.apache.fastandroid.databinding.FlowFragmentStateflowBinding
import com.tesla.framework.kt.onSingleClick
import com.tesla.framework.ui.fragment.BaseVBFragment

/**
 * Created by Jerry on 2022/6/20.
 */
class StateFlowDemoFragment:BaseVBFragment<FlowFragmentStateflowBinding>(FlowFragmentStateflowBinding::inflate) {

    private val mViewModel by viewModels<MutableStateViewModel>()

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mViewModel.startRefresh()
        mBinding.btnPlus.onSingleClick(interval = 200)  {
            mViewModel.increment()
        }

        mBinding.btnMinus.onSingleClick(interval = 200) {
            mViewModel.decrement()
        }
        lifecycleScope.launchWhenCreated {
            mViewModel.mCount.collect{
                mBinding.tvNumber.text = "$it"
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        mViewModel.stopRefresh()
    }
}