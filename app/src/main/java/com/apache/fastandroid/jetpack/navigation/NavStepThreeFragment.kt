package com.apache.fastandroid.jetpack.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FlowStepOneFragmentBinding
import com.apache.fastandroid.databinding.FlowStepThreeFragmentBinding
import com.apache.fastandroid.databinding.FragmentNavHomeBinding
import com.apache.fastandroid.databinding.FragmentStepOneBinding
import com.tesla.framework.ui.fragment.BaseVBFragment

/**
 * Created by Jerry on 2022/3/11.
 */
class NavStepThreeFragment:BaseVBFragment<FlowStepThreeFragmentBinding>(FlowStepThreeFragmentBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        viewBinding.nextButton.setOnClickListener {
            findNavController().navigate(R.id.next_action)
        }
    }

}