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
 * 系统会在新任务的根位置创建 activity，或将该 activity 放置在具有相同 affinity 的现有任务上。 如果任务的根位置已存在 activity 实例，则系统会通过调用现有实例的 onNewIntent() 方法（而非创建新的 activity 实例），向其传送 intent。
 */
class SingleTaskDemoActivity:BaseVBActivity<ActivityLaunchmodeSingletaskBinding>(ActivityLaunchmodeSingletaskBinding::inflate){

    override fun layoutInit(savedInstanceState: Bundle?) {
        super.layoutInit(savedInstanceState)

        mBinding.btnLaunchSelf.setOnClickListener {
            launchActivity<SingleTaskDemoActivity>(this)
        }


    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Logger.d("SingleTaskDemoActivity onNewIntent ")
    }
}