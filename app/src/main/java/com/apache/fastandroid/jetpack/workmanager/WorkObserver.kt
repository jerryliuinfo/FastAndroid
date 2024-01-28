package com.apache.fastandroid.jetpack.workmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf

/**
 * Created by Jerry on 2024/1/21.
 */
class WorkObserver(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {
        val data = workDataOf("result" to true)
        return Result.success(data)
    }
}