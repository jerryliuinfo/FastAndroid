package com.apache.fastandroid.demo.constraint.practice

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.ConstraintBarrierBinding
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * 用于控制barrier相对于指定控件的位置，例如 app:constraint_referenced_ids="textview1,textview2"
 * Barrier 将会使用ids中最大的一个宽/高 作为自己的宽高
 */
class ConstraintBarrierFragment: BaseBindingFragment<ConstraintBarrierBinding>(ConstraintBarrierBinding::inflate) {


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)


    }

}