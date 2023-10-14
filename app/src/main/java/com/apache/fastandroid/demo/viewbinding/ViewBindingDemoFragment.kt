package com.apache.fastandroid.demo.viewbinding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.apache.fastandroid.databinding.ActivityMainBinding
import com.tesla.framework.component.viewbinding.viewBinding
import com.tesla.framework.ui.fragment.BaseFragment

/**
 * Created by Jerry on 2023/9/29.
 */
class ViewBindingDemoFragment:BaseFragment() {


    private val binding by viewBinding(ActivityMainBinding::bind)

    override fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return binding.root
    }

}