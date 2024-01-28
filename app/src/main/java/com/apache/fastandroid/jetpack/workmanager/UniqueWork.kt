package com.apache.fastandroid.jetpack.workmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.apache.fastandroid.jetpack.workmanager.WorkManagerDemoFragment.Companion.WORK_MANAGER_TAG
import com.tesla.framework.component.logger.Logger

/**
 * Created by Jerry on 2024/1/21.
 */
class UniqueWork(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        Logger.d("$WORK_MANAGER_TAG UniqueWork begin doWork")
        Thread.sleep(5000)
        Logger.d("$WORK_MANAGER_TAG UniqueWork finish doWork")

        return Result.success()
    }
}