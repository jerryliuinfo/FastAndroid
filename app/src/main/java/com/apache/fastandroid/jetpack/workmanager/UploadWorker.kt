package com.apache.fastandroid.jetpack.workmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.tesla.framework.component.logger.Logger

/**
 * Created by Jerry on 2023/12/26.
 */
class UploadWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {
        Logger.d("UploadWorker doWork")
        Thread.sleep(30000)
        return Result.success()
    }
}