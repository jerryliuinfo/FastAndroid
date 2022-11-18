package com.apache.fastandroid.demo.guide

import android.os.Bundle
import com.apache.fastandroid.databinding.ActivityManifestDemoBinding
import com.apache.fastandroid.demo.guide.activity.SingleInstanceDemoActivity
import com.apache.fastandroid.demo.guide.activity.SingleTaskDemoActivity
import com.apache.fastandroid.demo.guide.activity.StandardTaskDemoActivity
import com.tesla.framework.kt.launchActivity
import com.tesla.framework.ui.activity.BaseVBActivity

/**
 * Created by Jerry on 2022/10/23.
 */
class ManifestDemoActivity:BaseVBActivity<ActivityManifestDemoBinding>(ActivityManifestDemoBinding::inflate) {
    override fun layoutInit(savedInstanceState: Bundle?) {
        super.layoutInit(savedInstanceState)


        mBinding.btnLaunchModeStandard.setOnClickListener {
            launchActivity<StandardTaskDemoActivity>(this)
        }

        /**
         * 系统会在新任务的根位置创建 activity，或将该 activity 放置在具有相同 affinity 的现有任务上。
         * 如果任务的根位置已存在 activity 实例，则系统会通过调用现有实例的 onNewIntent() 方法（而非创建新的 activity 实例），
         * 向其传送 intent
         */
        mBinding.btnLaunchModeSingleTask.setOnClickListener {
            launchActivity<SingleTaskDemoActivity>(this)
        }

        /**
         *与 singleTask" 相似，唯一不同的是系统不会将任何其他 Activity 启动到包含该实例的任务中。该 activity 始终是其任务中的唯一 activity。
         */
        mBinding.btnLaunchModeSingleInstance.setOnClickListener {
            launchActivity<SingleInstanceDemoActivity>(this)
        }


    }
}