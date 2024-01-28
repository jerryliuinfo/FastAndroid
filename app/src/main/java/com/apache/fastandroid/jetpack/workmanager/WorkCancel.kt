package com.apache.fastandroid.jetpack.workmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

/**
 * Created by Jerry on 2024/1/21.
 */
class WorkCancel(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        return Result.success()
    }
}