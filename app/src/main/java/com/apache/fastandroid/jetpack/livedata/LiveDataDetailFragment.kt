package com.apache.fastandroid.jetpack.livedata

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragementJetpackLivedataDetailBinding

import com.tesla.framework.ui.fragment.BaseVMFragment

/**
 * Created by Jerry on 2020/11/5.
 */
class LiveDataDetailFragment : BaseVMFragment<FragementJetpackLivedataDetailBinding>(FragementJetpackLivedataDetailBinding::inflate){


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)


        viewBinding.btnGoback.setOnClickListener {
            requireActivity().finish()
        }

    }

}