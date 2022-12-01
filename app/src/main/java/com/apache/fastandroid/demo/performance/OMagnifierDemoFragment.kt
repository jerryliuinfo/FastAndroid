package com.apache.fastandroid.demo.performance

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentMagnifierBinding
import com.blankj.utilcode.util.Utils
import com.microsoft.office.outlook.magnifierlib.Magnifier
import com.microsoft.office.outlook.magnifierlib.frame.FPSMonitorConfig
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.ui.fragment.BaseBindingFragment
import kotlinx.android.synthetic.main.fragment_magnifier.*

/**
 * Created by Jerry on 2022/1/3.
 */
class OMagnifierDemoFragment:BaseBindingFragment<FragmentMagnifierBinding>(FragmentMagnifierBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        button_fps_start.setOnClickListener {
            Magnifier.startMonitorFPS(
                FPSMonitorConfig.Builder(Utils.getApp()).lowPercentage(40 / 60f)  // show red tips, (2.0f / 3.0f) by default
                    .mediumPercentage(50 / 60f) // show yellow tips, (5.0f / 6.0f) by default
                    .refreshRate(60f) // defaultDisplay.refreshRate by default
                    .build()
            )
            Logger.d("after startMonitorFPS isEnabledFPSMonitor: ${Magnifier.isEnabledFPSMonitor()}")
        }

    }
}