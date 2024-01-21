package com.apache.fastandroid.jetpack.flow.share

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.apache.fastandroid.databinding.FragmentShareFlowBinding
import com.apache.fastandroid.jetpack.flow.vm.FlowShareViewModel
import com.tesla.framework.ui.fragment.BaseBindingFragment
import kotlinx.coroutines.launch

/**
 * Created by Jerry on 2023/12/10.
 */
class ShareFlowDemoFragment :
    BaseBindingFragment<FragmentShareFlowBinding>(FragmentShareFlowBinding::inflate) {

    private val mViewModel: FlowShareViewModel by viewModels()

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)


        lifecycleScope.launchWhenStarted {
            mViewModel.loginFlow.collect {
                if (it.isNotBlank()) {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            mViewModel.loginFlow2.collect {
                if (it.isNotBlank()) {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }


        }

        mBinding.btnFlowTimer.setOnClickListener {
            mViewModel.startLogin()
        }
        mBinding.btnFlowTimer2.setOnClickListener {
            mViewModel.startLogin2()
        }
    }
}