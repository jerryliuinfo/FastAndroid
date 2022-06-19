package com.example.flowpractice.download

import java.io.File

/**
 * 密封类的成员都是当前类的子类
 */
sealed class DownloadStatus {

     data class Done(val file:File):DownloadStatus()
     data class Error(val throwable: Throwable):DownloadStatus()
     data class Progress(val value:Int):DownloadStatus()
     object None:DownloadStatus()


}