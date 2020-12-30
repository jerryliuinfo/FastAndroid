package com.apache.fastandroid.demo.constraint.practice

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.ABaseFragment

/**
 *layout_constraintGuide_percent
 * app:layout_constraintGuide_begin=""
 * app:layout_constraintGuide_end=""
 *
 */
class ConstraintGuidelineBeginEndFragment:ABaseFragment() {
    override fun inflateContentView(): Int {
        return R.layout.constraint_guideline_begin_end
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)


    }
}