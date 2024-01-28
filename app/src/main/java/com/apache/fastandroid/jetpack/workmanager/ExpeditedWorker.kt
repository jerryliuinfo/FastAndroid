package com.apache.fastandroid.jetpack.workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

/**
 * Created by Jerry on 2024/1/21.
 */
class ExpeditedWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {


    override suspend fun doWork(): Result {
        return Result.success()
    }
}