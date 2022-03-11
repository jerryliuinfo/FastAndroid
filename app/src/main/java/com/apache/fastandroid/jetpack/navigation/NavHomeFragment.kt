package com.apache.fastandroid.jetpack.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentNavHomeBinding
import com.tesla.framework.ui.fragment.BaseVBFragment

/**
 * Created by Jerry on 2022/3/11.
 */
class NavHomeFragment:BaseVBFragment<FragmentNavHomeBinding>(FragmentNavHomeBinding::inflate) {
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        //需要加上这个，onCreateOptionsMenu 才会生效
        setHasOptionsMenu(true)

        viewBinding.navigateDestinationButton.setOnClickListener {
            findNavController().navigate(R.id.home_to_step_one)
        }
        viewBinding.navigateDestinationByDirection.setOnClickListener {
            findNavController().navigate(NavHomeFragmentDirections.homeToStepOne())
        }
        viewBinding.navigateActionButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.home_to_step_one))

        viewBinding.navigateActionButtonSafeArgs.setOnClickListener {
            val args = Bundle().apply {
                putInt("flowStepNumber",3)
            }
            findNavController().navigate(R.id.home_to_step_one,args)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu,menu)
    }
}