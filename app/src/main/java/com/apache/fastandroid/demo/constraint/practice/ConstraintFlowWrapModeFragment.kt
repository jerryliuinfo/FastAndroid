package com.apache.fastandroid.demo.constraint.practice

import android.os.Bundle
import android.view.LayoutInflater
import androidx.constraintlayout.helper.widget.Flow
import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.ABaseFragment
import kotlinx.android.synthetic.main.constraint_flow_wrap_mode.*


class ConstraintFlowWrapModeFragment:ABaseFragment() {
    override fun inflateContentView(): Int {
        return R.layout.constraint_flow_wrap_mode
    }


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        btn_align.setOnClickListener {
            flow.setWrapMode(Flow.WRAP_ALIGNED)
        }
        btn_packed.setOnClickListener {
            flow.setWrapMode(Flow.WRAP_CHAIN)
        }
        btn_none.setOnClickListener {
            flow.setWrapMode(Flow.WRAP_NONE)
        }
    }
}