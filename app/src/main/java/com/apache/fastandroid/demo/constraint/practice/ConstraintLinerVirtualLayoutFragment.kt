package com.apache.fastandroid.demo.constraint.practice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.ABaseFragment
import kotlinx.android.synthetic.main.constraint_place_holder.*
import kotlinx.android.synthetic.main.constraint_set.*


class ConstraintLinerVirtualLayoutFragment:ABaseFragment() {
    override fun inflateContentView(): Int {
        return R.layout.constraint_liner_virtual_layout
    }
}