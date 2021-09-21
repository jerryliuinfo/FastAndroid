package com.apache.fastandroid.demo.roudview

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_round_view.*

/**
 * Created by Jerry on 2021/9/10.
 */
class RoundViewDemoFragment:BaseFragment() {
    override fun inflateContentView(): Int {
        return R.layout.fragment_round_view
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)
        btn_switch_status.setOnClickListener {
            custom_text_view.isEnabled = !custom_text_view.isEnabled
        }
    }
}