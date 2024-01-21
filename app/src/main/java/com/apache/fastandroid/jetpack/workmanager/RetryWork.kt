package com.apache.fastandroid.jetpack.workmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.tesla.framework.component.logger.Logger
import kotlin.random.Random

/**
 * Created by Jerry on 2023/12/27.
 */

var startTime = System.currentTimeMillis()

class RetryWork(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        val nextInt = Random.nextInt(10)
        Logger.d("RetryWork doWork this:$this, nextInt:${nextInt},thread:${Thread.currentThread().name}, time diff:${(System.currentTimeMillis() - startTime) / 1000} ")
        startTime = System.currentTimeMillis()
        if (nextInt > 8) {
            return Result.success()
        }
        return Result.retry()
    }
}