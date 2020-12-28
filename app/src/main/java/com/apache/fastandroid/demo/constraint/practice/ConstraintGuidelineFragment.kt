package com.apache.fastandroid.demo.constraint.practice

import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.ABaseFragment

/**
 *layout_constraintGuide_percent
 * app:layout_constraintGuide_begin=""
 * app:layout_constraintGuide_end=""
 *
 */
class ConstraintGuidelineFragment:ABaseFragment() {
    override fun inflateContentView(): Int {
        return R.layout.constraint_guideline
    }
}