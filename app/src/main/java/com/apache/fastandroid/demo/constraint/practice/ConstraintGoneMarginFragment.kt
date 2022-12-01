package com.apache.fastandroid.demo.constraint.practice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.apache.fastandroid.databinding.ConstraintGoneMarginBinding
import com.tesla.framework.ui.fragment.BaseBindingFragment
import kotlinx.android.synthetic.main.constraint_gone_margin.*

/**
 *
 */
class ConstraintGoneMarginFragment: BaseBindingFragment<ConstraintGoneMarginBinding>(ConstraintGoneMarginBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)



        imageview1.setOnClickListener {
            it.visibility = View.GONE
        }
    }
}