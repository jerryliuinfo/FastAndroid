package com.apache.fastandroid.demo.progress

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentProgressBinding
import com.tesla.framework.component.progress.*
import com.tesla.framework.kt.runOnUiThread
import com.tesla.framework.ui.activity.BaseVBFragment2
import kotlin.concurrent.thread
import kotlin.random.Random

/**
 * Created by Jerry on 2023/3/4.
 */
class ProgressDemoFragment : BaseVBFragment2<FragmentProgressBinding>() {

    private val mCalculator: ICalculator = ProgressCalculator()

    private val mProgressManager = ProgressManager(ProgressConfig(3000, 15000, 100),mCalculator)

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
                    mBinding.btnProgress.text = "展示进度:$progress,实际进度:${mCalculator.getProgress()}, 当前耗时:${mProgressManager.costTime()} ms"
                }
            }

            override fun onFinish() {
                mCalculator.clearTask()

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

                mCalculator.addFinshedTask(Task1())
                Thread.sleep(Random.nextInt(200,500).toLong())

                mCalculator.addFinshedTask(Task2())

                Thread.sleep(Random.nextInt(800,1500).toLong())
                mCalculator.addFinshedTask(Task3())

                Thread.sleep(Random.nextInt(600,2000).toLong())

                mCalculator.addFinshedTask(Task4())
            }

        }

    }

}