package com.apache.fastandroid.demo.designmode

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.apache.fastandroid.demo.designmode.observer.StandardController
import com.tesla.framework.ui.fragment.BaseStatusFragmentNew
import kotlinx.android.synthetic.main.design_mode_observer.*

/**
 * Created by Jerry on 2021/8/11.
 */
class ObserverModeFragment: BaseStatusFragmentNew() {
    override fun inflateContentView(): Int {
        return R.layout.design_mode_observer
    }

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