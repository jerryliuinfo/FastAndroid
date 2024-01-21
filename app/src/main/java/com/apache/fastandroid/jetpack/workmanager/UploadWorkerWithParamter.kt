package com.apache.fastandroid.jetpack.workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.apache.fastandroid.jetpack.workmanager.WorkManagerDemoFragment.Companion.WORK_MANAGER_TAG
import com.tesla.framework.component.logger.Logger
import kotlinx.coroutines.delay

/**
 * Created by Jerry on 2023/12/26.
 */
class UploadWorkerWithParamter(context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {
    companion object{
         const val PARAM_IMAGE_URL = "image_url"
    }


    override suspend fun doWork(): Result {
        val imageUrl = inputData.getString(PARAM_IMAGE_URL) ?: Result.failure()
        uploadFile(imageUrl)
        return Result.success()
    }

    private suspend fun uploadFile(imageUrl: Any) {
        Logger.d("$WORK_MANAGER_TAG 图片:${imageUrl} 上传中...")
        delay(3000)
        Logger.d("$WORK_MANAGER_TAG 图片:${imageUrl} 上传完成...")
    }
}