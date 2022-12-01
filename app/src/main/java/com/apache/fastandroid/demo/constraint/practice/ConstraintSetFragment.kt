package com.apache.fastandroid.demo.constraint.practice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.ConstraintSetBinding
import com.tesla.framework.ui.fragment.BaseBindingFragment
import kotlinx.android.synthetic.main.constraint_set.*


class ConstraintSetFragment: BaseBindingFragment<ConstraintSetBinding>(ConstraintSetBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        container.setOnClickListener {
            onClick(it)
        }
    }

    fun onClick(view:View){
        val constraintLayout = view as ConstraintLayout
        val constraintSet = ConstraintSet().apply {
            clone(constraintLayout)
            connect(
                R.id.twitter,
                ConstraintSet.BOTTOM,
                ConstraintSet.PARENT_ID,
                ConstraintSet.BOTTOM
            )
        }
        constraintSet.applyTo(constraintLayout)
    }
}