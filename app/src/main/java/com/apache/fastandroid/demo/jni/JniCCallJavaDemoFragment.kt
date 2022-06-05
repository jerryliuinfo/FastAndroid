package com.apache.fastandroid.demo.jni

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.annotation.Keep
import com.apache.fastandroid.databinding.ActivityJniCallbackBinding
import com.apache.fastandroid.databinding.FragmentJniDemoBinding
import com.tesla.framework.kt.runOnUiThread
import com.tesla.framework.ui.fragment.BaseVBFragment

/**
 * Created by Jerry on 2022/6/5.
 */
class JniCCallJavaDemoFragment:BaseVBFragment<ActivityJniCallbackBinding>(ActivityJniCallbackBinding::inflate) {

    var hour = 0
    var minute = 0
    var second = 0

    override fun onResume() {
        super.onResume()
        second = 0
        minute = second
        hour = minute
        mBinding.hellojniMsg.text =
            stringFromJNI()
        println("JniCCallJavaDemoFragment onResume startTicks")
        startTicks()
    }

    override fun onPause() {
        super.onPause()
        println("JniCCallJavaDemoFragment onPause StopTicks")

        StopTicks()
    }

    /*
     * A function calling from JNI to update current timer
     */
    @Keep
    private fun updateTimer() {
        ++second
        if (second >= 60) {
            ++minute
            second -= 60
            if (minute >= 60) {
                ++hour
                minute -= 60
            }
        }
        val ticks = "" + hour + ":" +
                minute + ":" +
                second
        println("time:$ticks")
        runOnUiThread {

            mBinding.tickView.text = ticks
        }
    }


    external fun stringFromJNI(): String?
    external fun startTicks()
    external fun StopTicks()

    companion object{
        init {
            System.loadLibrary("hello-jnicallback")
        }
    }
}