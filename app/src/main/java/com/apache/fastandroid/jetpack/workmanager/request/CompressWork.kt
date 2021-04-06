package com.apache.fastandroid.jetpack.workmanager.request

import android.content.Context
import androidx.work.WorkInfo
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.blankj.utilcode.util.TimeUtils
import com.tesla.framework.common.util.log.NLog
import java.util.concurrent.TimeUnit

/**
 * Created by Jerry on 2021/4/6.
 */
class CompressWork(context: Context,workerParameters: WorkerParameters): Worker(context,workerParameters) {
    override fun doWork(): Result {
        NLog.d(UploadLogWorker.TAG, "CompressWork doWork")
        Thread.sleep(TimeUnit.SECONDS.toMillis(10))
        return Result.success();
    }
}