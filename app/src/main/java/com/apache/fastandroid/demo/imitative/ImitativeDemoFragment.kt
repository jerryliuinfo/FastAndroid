package com.apache.fastandroid.demo.imitative

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentImitativeDemoBinding
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2023/8/29.
 */
class ImitativeDemoFragment:BaseBindingFragment<FragmentImitativeDemoBinding>(FragmentImitativeDemoBinding::inflate) {
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

    }
}