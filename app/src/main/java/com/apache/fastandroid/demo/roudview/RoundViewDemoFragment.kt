package com.apache.fastandroid.demo.roudview

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentRoundViewBinding
import com.tesla.framework.ui.fragment.BaseBindingFragment
import kotlinx.android.synthetic.main.fragment_round_view.*

/**
 * Created by Jerry on 2021/9/10.
 */
class RoundViewDemoFragment:BaseBindingFragment<FragmentRoundViewBinding>(FragmentRoundViewBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)
        btn_switch_status.setOnClickListener {
            custom_text_view.isEnabled = !custom_text_view.isEnabled
        }
    }
}