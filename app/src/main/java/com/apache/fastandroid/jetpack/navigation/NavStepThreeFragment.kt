package com.apache.fastandroid.jetpack.navigation

import android.os.Bundle
import android.view.LayoutInflater
import androidx.navigation.fragment.findNavController
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FlowStepThreeFragmentBinding
import com.tesla.framework.ui.fragment.BaseVBFragment

/**
 * Created by Jerry on 2022/3/11.
 */
class NavStepThreeFragment:BaseVBFragment<FlowStepThreeFragmentBinding>(FlowStepThreeFragmentBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.nextButton.setOnClickListener {
            findNavController().navigate(R.id.next_action)
        }
    }

}