package com.apache.fastandroid.jetpack.workmanager

import android.os.Bundle
import android.view.LayoutInflater
import androidx.work.*
import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.BaseFragment
import com.apache.fastandroid.jetpack.workmanager.request.UploadLogWorker
import com.tesla.framework.applike.FrameworkApplication
import kotlinx.android.synthetic.main.fragment_workmananger_basic.*
import java.util.concurrent.TimeUnit

/**
 * Created by Jerry on 2021/4/6.
 */
class PeriodicWorkRequestDemoFragment: BaseFragment() {
    companion object{
        private val TAG = "PeriodicWorkRequest"

    }
    val constraint: Constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .setRequiresBatteryNotLow(true)
            .build()

    override fun inflateContentView(): Int {
        return R.layout.fragment_workmananger_period_request
    }
    private lateinit var instance:WorkManager
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)
        instance = WorkManager.getInstance(FrameworkApplication.getContext())
        btn_begin_then.setOnClickListener {
            doUpload()
        }
    }


    private fun doUpload(){
        val inputData = Data.Builder().putString("name", "Jerry").build()
        //周期性间隔不能少于15分钟
        var uploadWorkRequest = PeriodicWorkRequest.Builder(UploadLogWorker::class.java,15,TimeUnit.MINUTES)
                .setConstraints(constraint)
                //设置延迟执行时间
                .setInitialDelay(5, TimeUnit.SECONDS)
                //设置指数退避算法
                .setBackoffCriteria(BackoffPolicy.LINEAR, OneTimeWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)
                .addTag(TAG)
                .setInputData(inputData)
                .build()

        instance?.enqueue(uploadWorkRequest)
    }


}