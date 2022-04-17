package com.apache.fastandroid.demo.designmode

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.DesignModeObserverBinding
import com.apache.fastandroid.demo.designmode.observer.StandardController
import com.tesla.framework.ui.fragment.BaseVBFragment
import kotlinx.android.synthetic.main.design_mode_observer.*

/**
 * Created by Jerry on 2021/8/11.
 */
class ObserverModeFragment: BaseVBFragment<DesignModeObserverBinding>(DesignModeObserverBinding::inflate) {

    private lateinit var controller: StandardController
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        controller = StandardController()
        controller.addDefaultControlComponent()

        controller.setMediaPlayer()

        btn_change_status.setOnClickListener {
            controller.setPlayState(2)
        }


    }
}