package com.apache.fastandroid.demo.constraint.practice

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.ConstraintSpaceBinding
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.ui.fragment.BaseVBFragment
import kotlinx.android.synthetic.main.constraint_space.*

/**
 *
 * Space控件在布局中只占位置，而不去绘制渲染
 * 组件中的间隙用Space控件填充比用其它控件填充可以提高绘制效率
 * constrinatLayout中不能使用-margin的方式来完成传统布局中的一些错误的效果，但是可以借助space来实现类似的功能
 *
 */
class ConstraintSpaceFragment: BaseVBFragment<ConstraintSpaceBinding>(ConstraintSpaceBinding::inflate) {


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        var parent = constraint_layout.parent
        while (parent != null) {
            Logger.d( "parent: $parent")
            parent = parent.parent
        }
    }

}