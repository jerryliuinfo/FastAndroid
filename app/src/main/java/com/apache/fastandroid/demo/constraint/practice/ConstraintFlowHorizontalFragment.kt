package com.apache.fastandroid.demo.constraint.practice

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.ABaseFragment
import kotlinx.android.synthetic.main.constraint_flow_horizontal.*


class ConstraintFlowHorizontalFragment:ABaseFragment() {
    override fun inflateContentView(): Int {
        return R.layout.constraint_flow_horizontal
    }

    private var wrapMode = 0

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)
        view3.setOnClickListener{
            when (wrapMode) {
                0 -> {
                    wrapMode = 1
                }
                1 -> {
                    wrapMode = 2
                }
                2 -> {
                    wrapMode = 0
                }
            }
            flow.setWrapMode(wrapMode)
        }
    }
}