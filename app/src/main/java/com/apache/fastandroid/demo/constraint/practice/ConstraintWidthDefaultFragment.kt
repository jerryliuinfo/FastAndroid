package com.apache.fastandroid.demo.constraint.practice

import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.BaseStatusFragmentNew

/**
 *  ayout_constraintWidth_default默认是spread，占用所有的符合约束的空间也就是填充了中间所有的布局了，
 *  实现 如果需要背景刚好包含内容，这里只需要将layout_constraintWidth_default设置成wrap

 */
class ConstraintWidthDefaultFragment: BaseStatusFragmentNew() {
    override fun inflateContentView(): Int {
        return R.layout.constraint_width_default
    }
}