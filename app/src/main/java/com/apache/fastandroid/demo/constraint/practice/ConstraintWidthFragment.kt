package com.apache.fastandroid.demo.constraint.practice

import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.ABaseFragment

/**
 * 约束宽度在目标控件的范围内
 */
class ConstraintWidthFragment:ABaseFragment() {
    override fun inflateContentView(): Int {
        return R.layout.constraint_width
    }
}