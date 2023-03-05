package com.apache.fastandroid.demo.progress

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentProgressBinding
import com.tesla.framework.kt.runOnUiThread
import com.tesla.framework.ui.activity.BaseVBFragment2
import kotlin.concurrent.thread

/**
 * Created by Jerry on 2023/3/4.
 */
class ProgressDemoFragment : BaseVBFragment2<FragmentProgressBinding>() {

    private val mCaculator = ProgressCalculator()

    private val mProgressManager = ProgressManager(ProgressConfig(3000, 15000, 100))

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)


        mProgressManager.setProgressListener(object : IProgressListener {
            override fun onStart() {
                runOnUiThread {
                    mBinding.btnProgress.text = "开始..."
                }
            }

            override fun onProgress(progress: Int) {
                runOnUiThread {
                    mBinding.btnProgress.text = "展示进度:$progress,实际进度:${mProgressManager.mRealProgress}, 当前耗时:${mProgressManager.costTime()} ms"
                }
            }

            override fun onFinish() {
                mCaculator.clear()

                runOnUiThread {
//                    mBinding.btnProgress.text = "已结束"
                }
            }

        })

        mBinding.btnStart.setOnClickListener {
            thread {
                mProgressManager.start()
            }

        }

        mBinding.btnDoTask.setOnClickListener {
            thread {
                var realProgress = mCaculator.taskFinish(TaskInfo(10))
                mProgressManager.mRealProgress = realProgress

                Thread.sleep(200)

                realProgress = mCaculator.taskFinish(TaskInfo(30))
                mProgressManager.mRealProgress = realProgress

                Thread.sleep(800)
                realProgress = mCaculator.taskFinish(TaskInfo(25))
                mProgressManager.mRealProgress = realProgress

                Thread.sleep(300)

                realProgress = mCaculator.taskFinish(TaskInfo(35))
                mProgressManager.mRealProgress = realProgress

            }

        }

    }

}