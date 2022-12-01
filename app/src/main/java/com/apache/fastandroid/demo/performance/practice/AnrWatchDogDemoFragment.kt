package com.apache.fastandroid.demo.performance.practice

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.component.anr.AnrConfig
import com.apache.fastandroid.component.anr.AnrManager
import com.apache.fastandroid.databinding.PerformanceAnrWatchdogBinding
import com.github.anrwatchdog.ANRWatchDog
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.ui.fragment.BaseBindingFragment
import kotlinx.android.synthetic.main.performance_anr_watchdog.*

/**
 * Created by Jerry on 2020/12/28.
 */
class AnrWatchDogDemoFragment : BaseBindingFragment<PerformanceAnrWatchdogBinding>(PerformanceAnrWatchdogBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)
        btn_start.setOnClickListener {
            startAnr()
        }
        btn_thread_sleep.setOnClickListener {
            Thread.sleep(6000)
        }

        btn_InfiniteLopper.setOnClickListener {
           infiniteLoop()
        }
    }

    private fun startAnr(){
        val anrConfig = AnrConfig.with()
            .set_timeoutInterval(2000)
            .setIgnoreDebugger(false)

            //                .setReportThreadNamePrefix("App")
            .setReportMainThreadOnly()
            .setAnrInterceptor { duration ->
                val ret = 5000 - duration
                if (ret > 0)
                    NLog.w(ANRWatchDog.TAG, "Intercepted ANR that is too short ($duration ms), postponing for $ret ms.")
                return@setAnrInterceptor ret }
            .setAnrListener {
                it.printStackTrace()
                NLog.printStackTrace(TAG, it)
            }.build()
        AnrManager.getInstance().start(anrConfig)
    }

    private fun infiniteLoop() {
        var i = 0
        while (true) {
            i++
        }
    }

}