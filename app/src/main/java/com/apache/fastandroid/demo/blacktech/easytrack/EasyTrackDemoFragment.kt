package com.apache.fastandroid.demo.blacktech.easytrack

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentBlacktrackEasytrackBinding
import com.apache.fastandroid.demo.track.statistics.StatisticsUtils
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2022/1/15.
 */
class EasyTrackDemoFragment:BaseBindingFragment<FragmentBlacktrackEasytrackBinding>(FragmentBlacktrackEasytrackBinding::inflate) {


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)
        StatisticsUtils.init(requireContext())

    }
}