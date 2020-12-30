package com.apache.fastandroid.demo.constraint.practice

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.ui.fragment.ABaseFragment
import kotlinx.android.synthetic.main.constraint_layer.*
import kotlinx.android.synthetic.main.constraint_margin.*

class ConstraintLayerFragment:ABaseFragment() {

    override fun inflateContentView(): Int {
        return R.layout.constraint_layer
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        title.setOnClickListener{
            layer.apply {
                rotation = 45f
                translationX = 100f
                translationY = 100f
            }

        }
    }

}