package com.apache.fastandroid.jetpack.workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import kotlinx.coroutines.delay

/**
 * Created by Jerry on 2024/1/21.
 */
class ProgressWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    companion object {
        const val Progress = "Progress"
        private const val delayDuration = 1L
    }
    override suspend fun doWork(): Result {
        val firstUpdate = workDataOf(Progress to 0)
        val middleUpdate = workDataOf(Progress to 50)
        val lastUpdate = workDataOf(Progress to 100)
        // setProgress(firstUpdate)
        delay(delayDuration)
        // setProgress(middleUpdate)
        delay(delayDuration)
        // setProgress(lastUpdate)
        return Result.success()
    }
}