package com.apache.fastandroid.setting

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentAdvanceNavAboutBinding
import com.apache.fastandroid.jetpack.livedata.LiveDataWrongUsageActivity
import com.tesla.framework.ui.fragment.BaseVBFragment

/**
 * Created by Jerry on 2022/3/16.
 */
class SettingFragment:BaseVBFragment<FragmentAdvanceNavAboutBinding>(FragmentAdvanceNavAboutBinding::inflate) {
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        com.tesla.framework.kt.startActivity<AboutActivity>(requireContext())

    }
}