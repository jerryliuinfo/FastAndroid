package com.apache.fastandroid.demo.navigation

import android.os.Bundle
import android.view.LayoutInflater
import androidx.navigation.findNavController
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.NavigationFragment2Binding
import com.tesla.framework.ui.fragment.BaseVBFragment

/**
 * Created by Jerry on 2022/1/23.
 */
class NavFragment2:BaseVBFragment<NavigationFragment2Binding>(NavigationFragment2Binding::inflate) {
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        viewBinding.btn.setOnClickListener {
            it.findNavController().navigate(R.id.action_fragment2_to_fragment1)
        }

//        viewBinding.textview.text = arguments?.getString("name", "defaultName")+ arguments?.getInt("age",13)

        val args = arguments?.let {
             NavFragment2Args.fromBundle(it)
        }
        args?.name.let {
            viewBinding.btn.text =it
        }

        viewBinding.btnToActivity.setOnClickListener {
            it.findNavController().navigate(R.id.activity_bottom_navigation)
        }
    }
}