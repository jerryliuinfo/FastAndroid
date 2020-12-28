package com.apache.fastandroid.demo.constraint.practice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.ABaseFragment
import kotlinx.android.synthetic.main.constraint_group.*
import scala.reflect.internal.util.Statistics

/**
 *layout_constraintGuide_percent
 * app:layout_constraintGuide_begin=""
 * app:layout_constraintGuide_end=""
 *
 */
class ConstraintGroupFragment:ABaseFragment() {
    override fun inflateContentView(): Int {
        return R.layout.constraint_group
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        button.setOnClickListener {
            if (group.visibility == View.VISIBLE ){
                group.visibility = View.GONE
            }else{
                group.visibility = View.VISIBLE
            }
        }
    }
}