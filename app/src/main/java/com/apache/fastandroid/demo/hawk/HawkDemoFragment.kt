package com.apache.fastandroid.demo.hawk

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentHawkBinding
import com.apache.fastandroid.demo.hawk.interfaces.ILogInterceptor
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.ui.fragment.BaseVBFragment

/**
 * Created by Jerry on 2022/1/20.
 */
class HawkDemoFragment: BaseVBFragment<FragmentHawkBinding>(FragmentHawkBinding::inflate) {
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        viewBinding.tvHawk.setOnClickListener {
            timeHawkInit()
            timeHawkPut()
            timeHawkGet()
            timeHawkContains()
            timeHawkCount()
            timeHawkDelete()
        }

    }

    private fun timeHawkInit() {
        val startTime = System.currentTimeMillis()
        Hawk.init(requireContext()).apply {
            logInterceptor = object :ILogInterceptor{
                override fun onLog(message: String) {
                    Log.d("HAWK", message)
                }
            }
        }.build()
        val endTime = System.currentTimeMillis()
        Logger.d("Hawk.init: " + (endTime - startTime) + "ms")
    }

    private fun timeHawkPut() {
        val startTime = System.currentTimeMillis()
        Hawk.put("key", "Jerry")
        val endTime = System.currentTimeMillis()
        Logger.d("Hawk.put: " + (endTime - startTime) + "ms")
    }

    private fun timeHawkGet() {
        val startTime = System.currentTimeMillis()
        val key: String? = Hawk.get("key")
        val endTime = System.currentTimeMillis()
        Logger.d("Hawk.get: " + (endTime - startTime) + "ms")
    }

    private fun timeHawkCount() {
        val startTime = System.currentTimeMillis()
        Hawk.count()
        val endTime = System.currentTimeMillis()
        Logger.d("Hawk.count: " + (endTime - startTime) + "ms")
    }

    private fun timeHawkContains() {
        val startTime = System.currentTimeMillis()
        Hawk.contains("key")
        val endTime = System.currentTimeMillis()
        Logger.d("Hawk.count: " + (endTime - startTime) + "ms")
    }

    private fun timeHawkDelete() {
        val startTime = System.currentTimeMillis()
        Hawk.delete("key")
        val endTime = System.currentTimeMillis()
        Logger.d("Hawk.count: " + (endTime - startTime) + "ms")
    }
}