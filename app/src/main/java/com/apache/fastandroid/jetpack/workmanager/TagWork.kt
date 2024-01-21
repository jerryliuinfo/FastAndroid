package com.apache.fastandroid.jetpack.workmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.apache.fastandroid.jetpack.workmanager.WorkManagerDemoFragment.Companion.WORK_MANAGER_TAG

/**
 * Created by Jerry on 2023/12/27.
 */


class TagWork(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {

        Thread.sleep(5 * 1000)
       com.tesla.framework.component.logger.Logger.d("$WORK_MANAGER_TAG tag work run after 5000 -------------->")
        return Result.success()
    }
}