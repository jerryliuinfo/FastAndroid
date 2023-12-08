package com.apache.fastandroid.demo.blacktech

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.MainActivity
import com.apache.fastandroid.databinding.FragmentBlackTechDemoListBinding
import com.jakewharton.processphoenix.ProcessPhoenix
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2023/11/21.
 */
class BlackTechDemoListFragment:BaseBindingFragment<FragmentBlackTechDemoListBinding>(FragmentBlackTechDemoListBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.btnRestartApp.setOnClickListener {
            ProcessPhoenix.triggerRebirth(requireContext())
        }

        mBinding.btnRestartGivenIntent.setOnClickListener {
            ProcessPhoenix.triggerRebirth(requireContext(), Intent(requireContext(),MainActivity::class.java))
        }
    }
}