package com.apache.fastandroid.demo.guide.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import com.apache.fastandroid.databinding.ActivityLaunchmodeSingletaskBinding
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.kt.launchActivity
import com.tesla.framework.ui.activity.BaseVBActivity

/**
 * Created by Jerry on 2022/10/30.
 *
 */
class StandardTaskDemoActivity:BaseVBActivity<ActivityLaunchmodeSingletaskBinding>(ActivityLaunchmodeSingletaskBinding::inflate){

    override fun layoutInit(savedInstanceState: Bundle?) {
        super.layoutInit(savedInstanceState)

        mBinding.btnLaunchSelf.setOnClickListener {
            launchActivity<StandardTaskDemoActivity>(this)
        }


    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Logger.d("StandardTaskDemoActivity onNewIntent ")
    }
}