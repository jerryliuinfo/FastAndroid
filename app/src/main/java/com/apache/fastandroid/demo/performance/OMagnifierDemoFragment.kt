package com.apache.fastandroid.demo.performance

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import com.apache.fastandroid.databinding.FragmentMagnifierBinding
import com.blankj.utilcode.util.Utils
import com.tesla.framework.performance.omagnifier.Magnifier
import com.tesla.framework.performance.omagnifier.frame.FPSMonitorConfig
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.performance.omagnifier.memory.*
import com.tesla.framework.ui.fragment.BaseBindingFragment
import kotlinx.android.synthetic.main.fragment_magnifier.*

/**
 * Created by Jerry on 2022/1/3.
 */
class OMagnifierDemoFragment:BaseBindingFragment<FragmentMagnifierBinding>(FragmentMagnifierBinding::inflate) {

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.buttonFpsStart.setOnClickListener {
            Magnifier.startMonitorFPS(
                FPSMonitorConfig.Builder(Utils.getApp()).lowPercentage(40 / 60f)  // show red tips, (2.0f / 3.0f) by default
                    .mediumPercentage(50 / 60f) // show yellow tips, (5.0f / 6.0f) by default
                    .refreshRate(60f) // defaultDisplay.refreshRate by default
                    .build()
            )
            Log.i(TAG, "after startMonitorFPS isEnabledFPSMonitor: ${Magnifier.isEnabledFPSMonitor()}")
        }

        mBinding.buttonFpsStop.setOnClickListener {
            Magnifier.stopMonitorFPS()
            Log.i(TAG, "after stopMonitorFPS isEnabledFPSMonitor: ${Magnifier.isEnabledFPSMonitor()}")
        }

        mBinding.buttonFpsSleep.setOnClickListener {
            Thread.sleep(100)
        }

        mBinding.buttonStartMemory.setOnClickListener {
            Log.i(TAG, "before startMonitorMemory isEnableMemoryMonitor: ${Magnifier.isEnabledMemoryMonitor()}")
            Magnifier.startMonitorMemory(
                MemoryMonitorConfig.Builder().enableExceedLimitSample(
                    0.8f, // the benchmark for Exceed_Limit type sampler, if we reach out 80% the max, collect the metrics, 0.8f by default
                    10000 // the threshold for Exceed_Limit type sampler, 10s by default
                ).enableTimingSample(60 * 1000) // threshold for the timing checker, 1 min by default
                    .onSampleListener(object : MemoryMonitor.OnSampleListener {
                        override fun onSampleHeap(
                            heapMemoryInfo: HeapMemoryInfo,
                            sampleType: MemoryMonitor.SampleType
                        ) {
                            Log.d(TAG, "heapMemoryInfo:$heapMemoryInfo,sampleType:$sampleType")
                        }

                        override fun onSampleFile(
                            fileDescriptorInfo: FileDescriptorInfo,
                            sampleType: MemoryMonitor.SampleType
                        ) {
                            Log.d(TAG, "fileDescriptorInfo:${fileDescriptorInfo.fdMaxCount},sampleType:$sampleType")
                        }

                        override fun onSampleThread(
                            threadInfo: ThreadInfo,
                            sampleType: MemoryMonitor.SampleType
                        ) {
                            Log.d(TAG, "threadInfo:${threadInfo.threadsCount},sampleType:$sampleType")
                        }
                    }).build()
            )
            Log.i(TAG, "after startMonitorMemory isEnableMemoryMonitor: ${Magnifier.isEnabledMemoryMonitor()}")
        }

        mBinding.buttonStopMemory.setOnClickListener {
            Log.i(TAG, "before stopMonitorMemory isEnableMemoryMonitor: ${Magnifier.isEnabledMemoryMonitor()}")
            Magnifier.stopMonitorMemory()
            Log.i(TAG, "after stopMonitorMemory isEnableMemoryMonitor: ${Magnifier.isEnabledMemoryMonitor()}")
        }

        mBinding.buttonIncreaseMemory.setOnClickListener {
            val list = ArrayList<Any>()
            Thread {
                val used = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()
                val totalFree = Runtime.getRuntime().maxMemory() - used
                val arr = ByteArray((totalFree * 0.85).toInt()){
                    1
                }
                list.add(arr)
            }.start()
        }

        mBinding.buttonDumpMemoryImmediately.setOnClickListener {
            Magnifier.dumpMemoryImmediately(object : MemoryMonitor.OnSampleListener {
                override fun onSampleHeap(
                    heapMemoryInfo: HeapMemoryInfo,
                    sampleType: MemoryMonitor.SampleType
                ) {
                    Log.d(TAG, "heapMemoryInfo:$heapMemoryInfo,sampleType:$sampleType")
                }

                override fun onSampleFile(
                    fileDescriptorInfo: FileDescriptorInfo,
                    sampleType: MemoryMonitor.SampleType
                ) {
                    Log.d(TAG, "fileDescriptorInfo:${fileDescriptorInfo.fdMaxCount},sampleType:$sampleType")
                }

                override fun onSampleThread(
                    threadInfo: ThreadInfo,
                    sampleType: MemoryMonitor.SampleType
                ) {
                    Log.d(TAG, "threadInfo:${threadInfo.threadsCount},sampleType:$sampleType")
                }
            })
        }

    }
}