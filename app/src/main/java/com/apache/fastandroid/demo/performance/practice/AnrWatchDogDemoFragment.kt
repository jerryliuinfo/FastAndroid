package com.apache.fastandroid.demo.performance.practice

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.apache.fastandroid.app.FastApplication
import com.apache.fastandroid.databinding.PerformanceAnrWatchdogBinding
import com.tesla.framework.ui.fragment.ABaseFragment
import com.tesla.framework.ui.fragment.BaseTraceFragment
import kotlinx.android.synthetic.main.performance_anr_watchdog.*

/**
 * Created by Jerry on 2020/12/28.
 */
class AnrWatchDogDemoFragment : BaseTraceFragment<PerformanceAnrWatchdogBinding>() {
    override fun inflateContentView(): Int {
        return R.layout.performance_anr_watchdog
    }

    private lateinit var application: FastApplication

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        application = FastApplication.getApplication()
//        application.initAnrWatchDog();
//        application.anrWatchDog.setANRListener(application.silentListener)

        btn_thread_sleep.setOnClickListener {
            Thread.sleep(6 * 1000)
        }

        btn_InfiniteLopper.setOnClickListener {
           infiniteLoop()
        }
    }

    private fun infiniteLoop() {
        var i = 0
        while (true) {
            i++
        }
    }

}