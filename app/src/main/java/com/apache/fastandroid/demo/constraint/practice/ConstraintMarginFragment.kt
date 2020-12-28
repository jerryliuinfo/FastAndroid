package com.apache.fastandroid.demo.constraint.practice

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.apache.fastandroid.R
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.ui.fragment.ABaseFragment
import kotlinx.android.synthetic.main.constraint_margin.*

class ConstraintMarginFragment:ABaseFragment() {

    override fun inflateContentView(): Int {
        return R.layout.constraint_margin
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        var parent = constraint_layout.parent
        while (parent != null) {
            NLog.d(TAG, "parent: $parent")
            parent = parent.parent
        }
    }

}