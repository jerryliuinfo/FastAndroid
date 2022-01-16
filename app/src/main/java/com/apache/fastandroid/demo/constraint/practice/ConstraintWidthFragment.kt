package com.apache.fastandroid.demo.constraint.practice

import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.BaseStatusFragmentNew

/**
 * 约束宽度在目标控件的范围内
 */
class ConstraintWidthFragment: BaseStatusFragmentNew() {
    override fun getLayoutId(): Int {
        return R.layout.constraint_width
    }
}