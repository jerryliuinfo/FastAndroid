package com.apache.fastandroid.demo.guide.activity

import android.os.Bundle
import com.apache.fastandroid.databinding.ActivityFinishOntaskLaunchBinding
import com.tesla.framework.kt.launchActivity
import com.tesla.framework.kt.startActivity
import com.tesla.framework.ui.activity.BaseVBActivity

/**
 * Created by Jerry on 2022/10/26.
 */
class FinishOnTaskLaunchActivity:BaseVBActivity<ActivityFinishOntaskLaunchBinding>(ActivityFinishOntaskLaunchBinding::inflate) {

    override fun layoutInit(savedInstanceState: Bundle?) {
        super.layoutInit(savedInstanceState)

        mBinding.btnFinishOnTaskLaunch.setOnClickListener {
            startActivity<FinishOnTaskLaunchActivity>()

        }

    }
}