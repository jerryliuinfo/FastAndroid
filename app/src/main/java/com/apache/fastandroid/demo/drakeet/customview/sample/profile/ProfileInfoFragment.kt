package com.apache.fastandroid.demo.drakeet.customview.sample.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tesla.framework.ui.fragment.BaseFragment

/**
 * Created by Jerry on 2023/11/12.
 */
class ProfileInfoFragment:BaseFragment() {
    override fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return PersonInfoLayout(requireContext())
    }
}