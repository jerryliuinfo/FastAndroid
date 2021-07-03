package com.apache.fastandroid.demo.constraint.practice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.BaseFragment
import kotlinx.android.synthetic.main.constraint_gone_margin.*

/**
 *
 */
class ConstraintGoneMarginFragment: BaseFragment() {
    override fun inflateContentView(): Int {
        return R.layout.constraint_gone_margin
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)



        imageview1.setOnClickListener {
            it.visibility = View.GONE
        }
    }
}