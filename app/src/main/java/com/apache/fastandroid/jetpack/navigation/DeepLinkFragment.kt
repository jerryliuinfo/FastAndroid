package com.apache.fastandroid.jetpack.navigation

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.NavDeeplinkFragmentBinding
import com.tesla.framework.ui.fragment.BaseVBFragment

/**
 * Created by Jerry on 2022/3/11.
 */
class DeepLinkFragment:BaseVBFragment<NavDeeplinkFragmentBinding>(NavDeeplinkFragmentBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

    }
}