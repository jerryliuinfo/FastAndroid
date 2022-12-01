package com.apache.fastandroid.demo.constraint.practice

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.ConstraintGuidelineBeginEndBinding
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 *layout_constraintGuide_percent
 * app:layout_constraintGuide_begin=""
 * app:layout_constraintGuide_end=""
 *
 */
class ConstraintGuidelineBeginEndFragment: BaseBindingFragment<ConstraintGuidelineBeginEndBinding>(
    ConstraintGuidelineBeginEndBinding::inflate) {


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)


    }
}