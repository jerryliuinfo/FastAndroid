package com.apache.fastandroid.jetpack.workmanager.request

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.tesla.framework.common.util.log.NLog

/**
 * Created by Jerry on 2021/4/6.
 */
class UploadLogWorker(context:Context, workerParameters: WorkerParameters): Worker(context,workerParameters) {
    companion object{
         val TAG =  "UploadLogWorker"
    }
    override fun doWork(): Result {
        var name = inputData.getString("name")
        NLog.d(TAG, "UploadLogWorker doWork name: ${name}")
        return Result.success()
    }

}