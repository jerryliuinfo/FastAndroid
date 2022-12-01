package com.apache.fastandroid.demo.constraint.practice

import com.apache.fastandroid.databinding.ConstraintDimensionRatioBinding
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * 可以对宽高设置比例，前提是至少有一个约束维度设置为0dp, 有两种设置
 * 1.浮点值：表示宽和高的比例
 * 2.宽度：高度，表示宽度和高度之间形式的比例
 */
class ConstraintDimensionRatioFragment: BaseBindingFragment<ConstraintDimensionRatioBinding>(ConstraintDimensionRatioBinding::inflate) {

}