package com.apache.fastandroid.demo.crashreport

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.ActivityCrashReportBinding
import com.balsikandar.crashreporter.CrashReporter
import com.balsikandar.crashreporter.ui.CrashReporterActivity
import com.tesla.framework.ui.fragment.BaseVBFragment
import kotlin.concurrent.thread

/**
 * Created by Jerry on 2022/5/25.
 * https://github.com/MindorksOpenSource/CrashReporter
 */
class CrashReportDemoFragment:BaseVBFragment<ActivityCrashReportBinding>(ActivityCrashReportBinding::inflate) {

    var context1: Context? = null
    var mContext2: Context? = null

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.nullPointer.setOnClickListener {
            context1 = null
            context1!!.resources
        }

        mBinding.indexOutOfBound.setOnClickListener {
            val list = ArrayList<String>()
            list.add("hello")
            val crashMe = list[2]
        }

        mBinding.classCastExeption.setOnClickListener {
            val x: Any = 0
            println(x as String)
        }

        mBinding.arrayStoreException.setOnClickListener {
            val x = arrayOf<Any>()
            x[0] = 0

        }


        //Crashes and exceptions are also captured from other threads
        thread {
            try {
                context1 = null
//                context1!!.resources
            } catch (e: Exception) {
                //log caught Exception
                CrashReporter.logException(e)
            }
        }

        mContext2 = requireContext()
        mBinding.crashLogActivity.setOnClickListener {
            val intent = Intent(mContext2, CrashReporterActivity::class.java)
            startActivity(intent)
        }

    }
}