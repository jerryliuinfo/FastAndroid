package com.apache.fastandroid.demo.guide

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.ActivityPropertyBinding
import com.apache.fastandroid.demo.guide.activity.FinishOnTaskLaunchActivity
import com.tesla.framework.kt.launchActivity
import com.tesla.framework.ui.fragment.BaseVBFragment


class ActivityPropertyDemoFragment : BaseVBFragment<ActivityPropertyBinding>(ActivityPropertyBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.btnFinishOnTaskLaunch.setOnClickListener {
            launchActivity<FinishOnTaskLaunchActivity>(requireContext())
        }

        mBinding.btnManifestProperty.setOnClickListener {
            launchActivity<ManifestDemoActivity>(requireContext())
        }



    }


}