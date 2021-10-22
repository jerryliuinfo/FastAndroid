package com.apache.fastandroid.jetpack.workmanager

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import androidx.work.*
import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.BaseStatusFragmentNew
import com.apache.fastandroid.jetpack.workmanager.request.UploadLogWorker
import com.tesla.framework.applike.FApplication
import com.tesla.framework.common.util.log.NLog
import kotlinx.android.synthetic.main.fragment_workmananger_basic.*
import java.util.concurrent.TimeUnit

/**
 * Created by Jerry on 2021/4/6.
 */
class OneTimeWorkRequestDemoFragment: BaseStatusFragmentNew() {
    companion object{
        private val TAG = "UploadTag"

    }
    val constraint: Constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .setRequiresBatteryNotLow(true)
            .build()
    val inputData = Data.Builder().putString("name", "Jerry").build()
    var uploadWorkRequest = OneTimeWorkRequest.Builder(UploadLogWorker::class.java)
            .setConstraints(constraint)
            //设置延迟执行时间
            .setInitialDelay(15, TimeUnit.SECONDS)
            //设置指数退避算法
            .setBackoffCriteria(BackoffPolicy.LINEAR, OneTimeWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)
            .addTag(TAG)
            .setInputData(inputData)
            .build()
    override fun inflateContentView(): Int {
        return R.layout.fragment_workmananger_basic
    }
    private lateinit var instance:WorkManager
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)
        instance = WorkManager.getInstance(FApplication.getContext())
        btn_begin_then.setOnClickListener {
            doUpload()
        }
        tv_get_work_status.setOnClickListener {
            getWorkStatus()
        }
        tv_get_work_status_by_livdata.setOnClickListener {
            getWorkStatusByLiveData()
        }
        tv_cancel_by_tag.setOnClickListener {
            cancelWorkByTag()
        }
        tv_cancel_by_id.setOnClickListener {
            cancelWorkById()
        }
        btn_cancel_all.setOnClickListener {
            cancelAllWork()
        }
    }


    private fun doUpload(){
        instance?.enqueue(uploadWorkRequest)
    }

    private fun getWorkStatus(){
        var workInfos = instance.getWorkInfosByTag(TAG)
        NLog.d(TAG, "getWorkStatus workInfos:${workInfos}")
    }

    private fun getWorkStatusByLiveData(){
        var workInfos = instance?.getWorkInfosByTagLiveData(TAG)
        workInfos.observe(this, Observer {
           var workInfo:WorkInfo = it.get(0)
            NLog.d(TAG, "getWorkStatusByLiveData:${workInfo}")
        })
    }

    private fun cancelWorkByTag(){
        var operation = instance.cancelAllWorkByTag(TAG)
        NLog.d(TAG, "cancelWorkByTag:${operation}")
    }

    private fun cancelWorkById(){
        var operation = instance.cancelWorkById(uploadWorkRequest.id)
        NLog.d(TAG, "cancelWorkByTag:${uploadWorkRequest.id}")
    }

    private fun cancelAllWork(){
        var operation = instance.cancelAllWork()
        NLog.d(TAG, "cancelAllWork:${operation}")
    }
}