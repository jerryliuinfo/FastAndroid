package com.apache.fastandroid.demo.constraint.practice

import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.BaseFragment

/**
 * 在约束布局中，可以定义水平方向和垂直方向上的权重，只有在宽度或者高度为0dp(match_constraint)时才有效
 */
class ConstraintWeightFragment: BaseFragment() {
    override fun inflateContentView(): Int {
        return R.layout.constraint_weight
    }
}