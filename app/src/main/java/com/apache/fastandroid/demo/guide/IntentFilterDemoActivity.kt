package com.apache.fastandroid.demo.guide

import android.content.Intent
import android.os.Bundle
import com.apache.fastandroid.databinding.ActivityIntentFilterBinding
import com.apache.fastandroid.databinding.ActivityManifestDemoBinding
import com.apache.fastandroid.demo.guide.activity.SingleInstanceDemoActivity
import com.apache.fastandroid.demo.guide.activity.SingleTaskDemoActivity
import com.apache.fastandroid.demo.guide.activity.StandardTaskDemoActivity
import com.tesla.framework.kt.launchActivity
import com.tesla.framework.ui.activity.BaseVBActivity

/**
 * Created by Jerry on 2022/10/23.
 */
class IntentFilterDemoActivity:BaseVBActivity<ActivityIntentFilterBinding>(ActivityIntentFilterBinding::inflate) {
    override fun layoutInit(savedInstanceState: Bundle?) {
        super.layoutInit(savedInstanceState)


        val msg = intent.getStringExtra(Intent.EXTRA_TEXT)?: "default text"
        mBinding.tvResult.text = msg


    }
}