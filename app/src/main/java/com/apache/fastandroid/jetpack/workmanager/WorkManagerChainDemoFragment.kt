package com.apache.fastandroid.jetpack.workmanager

import android.os.Bundle
import android.view.LayoutInflater
import androidx.work.*
import com.apache.fastandroid.R
import com.apache.fastandroid.artemis.base.BaseFragment
import com.apache.fastandroid.jetpack.workmanager.request.CompressWork
import com.apache.fastandroid.jetpack.workmanager.request.UpdateLocalWork
import com.apache.fastandroid.jetpack.workmanager.request.UploadLogWorker
import com.apache.fastandroid.jetpack.workmanager.request.PublishWorker
import com.tesla.framework.Global
import com.tesla.framework.applike.FrameworkApplication
import kotlinx.android.synthetic.main.fragment_workmananger_basic.*
import kotlinx.android.synthetic.main.fragment_workmananger_basic.btn_begin_then
import kotlinx.android.synthetic.main.fragment_workmananger_chain.*

/**
 * Created by Jerry on 2021/4/6.
 */
class WorkManagerChainDemoFragment: BaseFragment() {
    companion object{
        private val TAG = "PeriodicWorkRequest"


    }
    val constraint: Constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .setRequiresBatteryNotLow(true)
            .build()

    override fun inflateContentView(): Int {
        return R.layout.fragment_workmananger_chain
    }
    private lateinit var instance:WorkManager
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)
        instance = WorkManager.getInstance(FrameworkApplication.getContext())
        btn_begin_then.setOnClickListener {
            beginThen()
        }
        btn_combile.setOnClickListener {
            comine()
        }
    }

    private val compressWork = OneTimeWorkRequest.Builder(CompressWork::class.java).build()
    private val updateLocalDataWorker = OneTimeWorkRequest.Builder(UpdateLocalWork::class.java).build()
    private val uploadWorker = OneTimeWorkRequest.Builder(UploadLogWorker::class.java).build()
    private val publishWorker = OneTimeWorkRequest.Builder(PublishWorker::class.java).build()


    private fun beginThen(){
        var operation = WorkManager.getInstance(FrameworkApplication.getContext()).beginWith(compressWork).then(updateLocalDataWorker).enqueue()
    }

    private fun comine(){
        var workContinuation1 = WorkManager.getInstance(FrameworkApplication.getContext()).beginWith(compressWork).then(updateLocalDataWorker)
        var workContinuation2 = WorkManager.getInstance(FrameworkApplication.getContext()).beginWith(uploadWorker)
        val taskList = ArrayList<WorkContinuation>().apply {
            add(workContinuation1)
            add(workContinuation2)
        }
        WorkContinuation.combine(taskList).then(publishWorker).enqueue()
    }


}