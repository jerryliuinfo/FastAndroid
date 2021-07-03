package com.apache.fastandroid.demo.constraint.practice

import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.BaseFragment

/**
 * 当TextView的宽度为wrapper时,如果textview的文字很长超过了控件大小时，此时margin无效，改为0dp就可以了
 *
 */
class ConstrainMatchConstraintFragment: BaseFragment() {
    override fun inflateContentView(): Int {
        return R.layout.constraint_match_constraint
    }
}