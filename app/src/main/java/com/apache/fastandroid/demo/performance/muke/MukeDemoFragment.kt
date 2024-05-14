package com.apache.fastandroid.demo.performance.muke

import android.os.Bundle
import android.os.Debug
import android.view.LayoutInflater
import androidx.core.os.TraceCompat
import com.apache.fastandroid.databinding.FragmentMukePerformanceBinding
import com.tesla.framework.ui.fragment.BaseBindingFragment
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Created by Jerry on 2024/4/20.
 */
class MukeDemoFragment:BaseBindingFragment<FragmentMukePerformanceBinding>(FragmentMukePerformanceBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.btnTraceView.setOnClickListener {
            traceViewUsage()
        }

        mBinding.btnSystrace.setOnClickListener {
            systraceUsage()
        }
    }

    /**
     * 生成的文件在 sdcard/Android/data/packagename/files
     */
    private fun traceViewUsage(){
        //将时间作为文件名，避免被覆盖
        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        Debug.startMethodTracing(dateFormat.format(Date()))

        Debug.stopMethodTracing()
    }


    private fun systraceUsage(){
        try {
            TraceCompat.beginSection("Section1")
            Thread.sleep(500)
        } finally {
            TraceCompat.endSection()
        }

        try {
            TraceCompat.beginSection("Section2")
            Thread.sleep(300)
        } finally {
            TraceCompat.endSection()
        }


    }


    /**
     * 异步优化
     * 核心思想：主线程分担子线程任务，并行减少时间
     */
    private fun asyncOptimize(){

    }
}