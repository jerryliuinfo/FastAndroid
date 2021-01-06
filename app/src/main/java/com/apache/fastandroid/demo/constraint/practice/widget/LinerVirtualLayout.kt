package com.apache.fastandroid.demo.constraint.practice.widget

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.VirtualLayout

/**
 * Created by Jerry on 2021/1/6.
 */
class LinerVirtualLayout(context: Context?, attrs: AttributeSet?) : VirtualLayout(context, attrs) {

    private val mConstraintSet = ConstraintSet().apply {
        isForceId = false
    }

    override fun updatePreLayout(container: ConstraintLayout?) {
        super.updatePreLayout(container)
        mConstraintSet.clone(container)

        val viewIds = referencedIds
        for (i in 1 until mCount){
            val current = viewIds[i]
            val before = viewIds[i - 1]
            mConstraintSet.connect(current,ConstraintSet.START,before,ConstraintSet.START)
            mConstraintSet.connect(current,ConstraintSet.TOP,before,ConstraintSet.BOTTOM)
            mConstraintSet.applyTo(container)
        }
    }
}