package com.apache.fastandroid.jetpack.navigation.advance.home

import android.os.Bundle
import android.view.LayoutInflater
import androidx.navigation.findNavController
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentAdvanceNavTitleBinding
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2022/3/12.
 */
class TitleFragment:BaseBindingFragment<FragmentAdvanceNavTitleBinding>(FragmentAdvanceNavTitleBinding::inflate) {
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.aboutBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_title_to_about)
        }
    }
}