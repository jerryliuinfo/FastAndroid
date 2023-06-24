package com.apache.fastandroid.demo.countdown

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentCountdownTimerBinding
import com.tesla.framework.component.countdown.*
import com.tesla.framework.kt.runOnUiThread
import com.tesla.framework.ui.activity.BaseVBFragment2

/**
 * Created by Jerry on 2023/3/4.
 */
class CountDownDemoFragment : BaseVBFragment2<FragmentCountdownTimerBinding>() {

    private var mCountDownTimerTask:ICountDownAction ?= null

    private var mCountDownByCountDownTimer:ICountDownAction ?= null

    private var mLifeCycleCountDownTimerTask:ICountDownAction ?= null

    private var mCountDownByCoroutine:ICountDownAction ?= null



    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.btnTimerTask.setOnClickListener {
            mCountDownTimerTask?.stop()
            mCountDownTimerTask = CountDownByTimer(count = 60, delay = 0, period = 1000, object :ICountDownListener{
                override fun onTick(count: Long) {
                    runOnUiThread {
                        mBinding.btnProgress.text = "$count 秒后重发"
                    }
                }

                override fun onFinish() {
                    mCountDownTimerTask = null
                }

            }).apply {
                start()
            }
        }


        mBinding.btnCountDownTimer.setOnClickListener {
            mCountDownByCountDownTimer?.stop()
            mCountDownByCountDownTimer = CountDownByCountDownTimer(millsInFuture = 6000, interval = 1000, object :ICountDownListener{
                override fun onTick(count: Long) {
                    runOnUiThread {
                        mBinding.btnProgress.text = "${count/1000} 秒后重发"
                    }
                }

                override fun onFinish() {
                    mCountDownTimerTask = null
                }

            }).apply {
                start()
            }

        }

        mBinding.btnLifecycleTimerTask.setOnClickListener {
            mLifeCycleCountDownTimerTask?.stop()
            mLifeCycleCountDownTimerTask = LifeCycleCountDownByTimer(count = 60, delay = 0, period = 1000,this, object :ICountDownListener{
                override fun onTick(count: Long) {
                    runOnUiThread {
                        mBinding.btnProgress.text = "$count 秒后重发333"
                    }
                }

                override fun onFinish() {
                    mCountDownTimerTask = null
                }

            }).apply {
                start()
            }
        }

        mBinding.btnCountDownByCoroutine.setOnClickListener {
            mCountDownByCoroutine?.stop()
            mCountDownByCoroutine = CountDownByCoroutine(60,1000,object :ICountDownListener{
                override fun onTick(count: Long) {
                    runOnUiThread {
                        mBinding.btnProgress.text = "$count 秒后重发444"
                    }
                }

                override fun onFinish() {
                }

            }).start()
        }
    }

}