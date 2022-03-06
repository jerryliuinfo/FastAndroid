package com.apache.fastandroid.demo.launchermode

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentLauncherModeBinding
import com.tesla.framework.ui.fragment.BaseVMFragment

/**
 * Created by Jerry on 2022/3/2.
 */
class LaunchModeDemoFragment:BaseVMFragment<FragmentLauncherModeBinding>(FragmentLauncherModeBinding::inflate) {
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

    }
}