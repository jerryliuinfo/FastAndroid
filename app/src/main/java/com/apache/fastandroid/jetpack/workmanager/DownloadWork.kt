package com.apache.fastandroid.jetpack.workmanager

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.io.IOException

/**
 * Created by Jerry on 2024/1/28.
 */
class DownloadWork(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {

        repeat(100) {

            try {
                downloadSynchronously("https://www.google.com")
            } catch (e: IOException) {
                return ListenableWorker.Result.failure()
            }

        }

        return Result.success()
    }

    private fun downloadSynchronously(url:String) {

    }
}