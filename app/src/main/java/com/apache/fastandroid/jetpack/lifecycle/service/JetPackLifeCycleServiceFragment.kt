package com.apache.fastandroid.jetpack.lifecycle.service

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentJetpackLifecycleServiceBinding
import com.tesla.framework.ui.fragment.BaseVBFragment
import kotlinx.android.synthetic.main.fragment_jetpack_lifecycle_service.*

/**
 * Created by Jerry on 2020/10/31.
 */
class JetPackLifeCycleServiceFragment: BaseVBFragment<FragmentJetpackLifecycleServiceBinding>(FragmentJetpackLifecycleServiceBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        btn_start_service.setOnClickListener {
            context?.let { it1 -> MyService.start(it1) }
        }

        btn_stop_service.setOnClickListener {
            context?.let { it1 -> MyService.stop(it1) }
        }
    }


}