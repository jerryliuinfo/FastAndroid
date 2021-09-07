package com.apache.fastandroid.demo.constraint.practice

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.BaseStatusFragmentNew
import com.tesla.framework.common.util.log.FastLog
import kotlinx.android.synthetic.main.constraint_margin.*

class ConstraintMarginFragment: BaseStatusFragmentNew() {

    override fun inflateContentView(): Int {
        return R.layout.liner_margin
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