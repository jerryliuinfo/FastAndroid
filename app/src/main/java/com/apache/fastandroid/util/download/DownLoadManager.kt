package com.example.flowpractice.download

import android.util.Log
import com.tesla.framework.kt.copyToOut
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File

object DownLoadManager {
    private val TAG: String by lazy { DownLoadManager::class.java.simpleName }

    fun download(url: String, file: File): Flow<DownloadStatus> {
        return flow {
            Log.d(TAG, "start download")
            val request = Request.Builder().url(url).build()
            val response = OkHttpClient.Builder().build().newCall(request).execute()
            if (response.isSuccessful) {
                response.body!!.let { body ->
                    //文件到总大小
                    val total = body.contentLength()
                    //写文件 use函数可以自动关闭IO流操作
                    file.outputStream().use { output ->
                        val input = body.byteStream()
                        var emittedProgress = 0
                        input.copyToOut(output) { bytesCopied ->
                            delay(100)
                            val progress = bytesCopied * 100 / total
                            if (progress - emittedProgress > 5 || progress == 100) {
                                emittedProgress = progress.toInt()
                            }
                            Log.d(TAG, "downloading $emittedProgress")
                            emit(DownloadStatus.Progress(emittedProgress))
                        }
                        emit(DownloadStatus.Done(file))
                    }
                }
            }else{
                throw java.io.IOException(response.toString())
            }
        }.catch { e ->
            file.delete()
            emit(DownloadStatus.Error(e))
        }.flowOn(Dispatchers.IO)
    }

}