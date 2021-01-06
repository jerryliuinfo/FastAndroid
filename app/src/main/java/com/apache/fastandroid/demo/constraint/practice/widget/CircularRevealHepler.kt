package com.apache.fastandroid.demo.constraint.practice.widget

import android.content.Context
import android.util.AttributeSet
import android.view.ViewAnimationUtils
import androidx.constraintlayout.widget.ConstraintHelper
import androidx.constraintlayout.widget.ConstraintLayout
import com.tesla.framework.common.util.log.NLog
import kotlin.math.hypot

/**
 * Created by Jerry on 2020/12/29.
 */
class CircularRevealHepler(context: Context?, attrs: AttributeSet) : ConstraintHelper(context, attrs) {
    companion object{
        private const val TAG = "CircularRevealHepler"
    }
    /**
     * 所有的布局完成后执行 updatePostLayout,在这个方法里面根据referencedIds找到对应的id执行动画
     */
    override fun updatePostLayout(container: ConstraintLayout) {
        super.updatePostLayout(container)

        referencedIds.forEach {
            val view = container.getViewById(it)
            NLog.d(TAG, "updatePostLayout view id: %s , width: %s, height: %s",it,view.width,view.height )
            //hypot 计算三角形斜边
            val radius = hypot(view.width.toFloat(),view.height.toFloat())
            ViewAnimationUtils.createCircularReveal(view,0,0,0f,radius)
                    .setDuration(2000)
                    .start()
        }
    }
}