package com.apache.fastandroid.demo.constraint.practice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.apache.fastandroid.databinding.ConstraintGroupBinding
import com.tesla.framework.ui.fragment.BaseVBFragment
import kotlinx.android.synthetic.main.constraint_group.*

/**
 * 对一组控件同时进行显示或隐藏
 *
 *
 */
class ConstraintGroupFragment: BaseVBFragment<ConstraintGroupBinding>(ConstraintGroupBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        button.setOnClickListener {
            if (group.visibility == View.VISIBLE ){
                group.visibility = View.GONE
            }else{
                group.visibility = View.VISIBLE
            }
        }
    }
}