package com.apache.fastandroid.jetpack.workmanager.chain

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.apache.fastandroid.jetpack.workmanager.WorkManagerDemoFragment.Companion.WORK_MANAGER_TAG
import com.tesla.framework.component.logger.Logger

/**
 * Created by Jerry on 2023/12/27.
 */


class PlantName2Work(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {

        Logger.d("$WORK_MANAGER_TAG PlantName2Work run -------------->")
        Thread.sleep(5000)
        return Result.success()
    }
}