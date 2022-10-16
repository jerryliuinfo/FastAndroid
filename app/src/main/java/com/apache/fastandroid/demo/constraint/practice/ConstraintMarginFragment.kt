package com.apache.fastandroid.demo.constraint.practice

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.LinerMarginBinding
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.ui.fragment.BaseVBFragment
import kotlinx.android.synthetic.main.constraint_margin.*

class ConstraintMarginFragment: BaseVBFragment<LinerMarginBinding>(LinerMarginBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        var parent = constraint_layout.parent
        while (parent != null) {
            Logger.d("parent: $parent")
            parent = parent.parent
        }
    }

}