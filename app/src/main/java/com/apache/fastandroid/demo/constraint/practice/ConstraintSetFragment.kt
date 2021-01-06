package com.apache.fastandroid.demo.constraint.practice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.ABaseFragment
import kotlinx.android.synthetic.main.constraint_place_holder.*
import kotlinx.android.synthetic.main.constraint_set.*


class ConstraintSetFragment:ABaseFragment() {
    override fun inflateContentView(): Int {
        return R.layout.constraint_set
    }

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