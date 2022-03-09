package com.apache.fastandroid.demo.navigation

import android.os.Bundle
import android.view.LayoutInflater
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.NavigationFragment1Binding
import com.tesla.framework.ui.fragment.BaseVBFragment

/**
 * Created by Jerry on 2022/1/23.
 */
class NavFragment1:BaseVBFragment<NavigationFragment1Binding>(NavigationFragment1Binding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        viewBinding.jumpByAction.setOnClickListener {
            it.findNavController().navigate(R.id.action_fragment1_to_fragment2)
        }

        viewBinding.actionWithId.setOnClickListener {
            it.findNavController().navigate(R.id.fragment2)
        }

        viewBinding.actionWithBundle.setOnClickListener {
            it.findNavController().navigate(R.id.action_fragment1_to_fragment2,Bundle().apply {
                putString("name","jerry")
                putInt("age",10)
            })
        }
        viewBinding.actionWithSafeArgs.setOnClickListener {
            val directions:NavDirections = NavFragment1Directions.actionFragment1ToFragment2("Jackson")
            it.findNavController().navigate(directions)
        }
    }
}