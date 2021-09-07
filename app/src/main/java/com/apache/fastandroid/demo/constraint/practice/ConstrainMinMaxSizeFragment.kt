package com.apache.fastandroid.demo.constraint.practice

import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.BaseStatusFragmentNew

/**
 * 给宽/高 设置最小最大值，则其宽度/高度 属性必须设置为wrap_content
 */
class ConstrainMinMaxSizeFragment: BaseStatusFragmentNew() {
    override fun inflateContentView(): Int {
        return R.layout.constraint_min_max_size
    }
}