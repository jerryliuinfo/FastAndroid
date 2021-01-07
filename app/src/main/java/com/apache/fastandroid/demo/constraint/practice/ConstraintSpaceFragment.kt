package com.apache.fastandroid.demo.constraint.practice

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.tesla.framework.common.util.log.FastLog
import com.tesla.framework.ui.fragment.ABaseFragment
import kotlinx.android.synthetic.main.constraint_space.*

/**
 *
 * Space控件在布局中只占位置，而不去绘制渲染
 * 组件中的间隙用Space控件填充比用其它控件填充可以提高绘制效率
 */
class ConstraintSpaceFragment:ABaseFragment() {

    override fun inflateContentView(): Int {
        return R.layout.constraint_space
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        var parent = constraint_layout.parent
        while (parent != null) {
            FastLog.d(TAG, "parent: $parent")
            parent = parent.parent
        }
    }

}