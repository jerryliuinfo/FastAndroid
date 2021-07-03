package com.apache.fastandroid.demo.constraint.practice

import android.os.Bundle
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.BaseFragment
import kotlinx.android.synthetic.main.constraint_set.*


class ConstraintSetSwitchLayoutFragment: BaseFragment() {
    override fun inflateContentView(): Int {
        return R.layout.activity_constraint_start
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        container.setOnClickListener {
            onClick(it)
        }
    }

    private var layoutId = R.layout.activity_constraint_start
    private lateinit var endContainer:View

    fun onClick(view:View){
        val constraintLayout = view as ConstraintLayout

        val constraintSet = ConstraintSet().apply {
            isForceId = false
            clone(this@ConstraintSetSwitchLayoutFragment.activity,
                    R.layout.activity_constraint_end
            )
        }
        TransitionManager.beginDelayedTransition(constraintLayout)
        constraintSet.applyTo(constraintLayout)
    }

}