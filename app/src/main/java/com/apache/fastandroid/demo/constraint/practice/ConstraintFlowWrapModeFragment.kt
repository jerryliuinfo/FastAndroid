package com.apache.fastandroid.demo.constraint.practice

import android.os.Bundle
import android.view.LayoutInflater
import androidx.constraintlayout.helper.widget.Flow
import com.apache.fastandroid.databinding.ConstraintFlowWrapModeBinding
import com.tesla.framework.ui.fragment.BaseBindingFragment
import kotlinx.android.synthetic.main.constraint_flow_wrap_mode.*

/**
 * wrap none : 简单地把constraint_referenced_ids里面的元素组成chain,即使空间不够
 * rap chain : 根据空间的大小和元素的大小组成一条或者多条 chain
 * wrap aligned : wrap chain类似，但是会对齐
 *
 *         app:flow_maxElementsWrap="4"：每行最多显示几个元素

 */
class ConstraintFlowWrapModeFragment: BaseBindingFragment<ConstraintFlowWrapModeBinding>(ConstraintFlowWrapModeBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        btn_align.setOnClickListener {
            flow.setWrapMode(Flow.WRAP_ALIGNED)
        }
        btn_packed.setOnClickListener {
            flow.setWrapMode(Flow.WRAP_CHAIN)
        }
        btn_none.setOnClickListener {
            flow.setWrapMode(Flow.WRAP_NONE)
        }
    }
}