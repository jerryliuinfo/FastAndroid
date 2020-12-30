package com.apache.fastandroid.demo.constraint.practice.widget

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintHelper
import androidx.constraintlayout.widget.ConstraintLayout

/**
 * Created by Jerry on 2020/12/29.
 */
class CircularRevealHepler(context: Context?, attrs: AttributeSet) : ConstraintHelper(context, attrs) {
    override fun updatePostLayout(container: ConstraintLayout) {
        super.updatePostLayout(container)

        referencedIds.forEach {
            val view = container.getViewById(it)
        }
    }
}