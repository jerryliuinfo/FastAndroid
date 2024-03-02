package com.apache.fastandroid.demo.guide.bestpractice.test

import android.os.Bundle
import com.apache.fastandroid.databinding.FragmentBestPracticeTestBasicBinding
import com.tesla.framework.ui.activity.BaseBindingActivity

/**
 * Created by Jerry on 2024/3/2.
 */
class BasicTestActivity: BaseBindingActivity<FragmentBestPracticeTestBasicBinding>() {
    override fun layoutInit(savedInstanceState: Bundle?) {
        super.layoutInit(savedInstanceState)

        mBinding.btnChange.setOnClickListener {
            mBinding.tvContent.text = "改变成功"
        }

    }
}