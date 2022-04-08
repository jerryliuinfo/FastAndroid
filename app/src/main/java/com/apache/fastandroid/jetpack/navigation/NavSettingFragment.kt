package com.apache.fastandroid.jetpack.navigation

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.NavSettingsFragmentBinding
import com.apache.fastandroid.setting.AboutActivity
import com.tesla.framework.kt.launchActivity
import com.tesla.framework.ui.fragment.BaseVBFragment

/**
 * Created by Jerry on 2022/3/12.
 */
class NavSettingFragment:BaseVBFragment<NavSettingsFragmentBinding>(NavSettingsFragmentBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)


        launchActivity<AboutActivity>(requireContext())
    }
}
