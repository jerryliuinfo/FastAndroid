package com.apache.fastandroid.demo.progress

/**
 * Created by Jerry on 2023/3/4.
 */
interface IProgressListener {

    fun onStart()

    fun onProgress(progress:Int)

    fun onFinish()

}
