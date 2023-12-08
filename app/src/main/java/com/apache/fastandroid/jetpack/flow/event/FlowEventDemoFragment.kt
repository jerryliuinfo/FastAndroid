package com.apache.fastandroid.jetpack.flow.event

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.apache.fastandroid.databinding.FlowFragmentEventBinding
import com.apache.fastandroid.databinding.FlowFragmentRoomBinding
import com.apache.fastandroid.jetpack.flow.FlowEventViewModel
import com.apache.fastandroid.jetpack.flow.adapter.UserAdapter
import com.apache.fastandroid.jetpack.flow.vm.FlowUserViewModel
import com.tesla.framework.ui.fragment.BaseBindingFragment
import com.tesla.framework.ui.fragment.BaseBindingFragmentRef
import kotlinx.coroutines.flow.onEach

/**
 * Created by Jerry on 2022/6/19.
 */
class FlowEventDemoFragment:BaseBindingFragmentRef<FlowFragmentEventBinding>() {

    private val mViewModel by viewModels<FlowEventViewModel>()

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.btnEvent1.setOnClickListener {
            mViewModel.reloadApps()
        }

        mBinding.btnEvent2.setOnClickListener {
            mViewModel.packageChanged("com.apache.abc", "package remove")
        }

        mViewModel.effect.onEach {
            when(it){
                is FlowEventViewModel.Effect.ReloadApps ->{
                    println("recevier reload app event")
                }
                is FlowEventViewModel.Effect.PackageChanged ->{
                    println("recevier PackageChanged app event, packageName:${it.packageName}, action:${it.action}")
                }
            }
        }

    }
}