package com.tesla.framework.ui.widget.snaphelper

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.tesla.framework.common.util.log.NLog

/**
 * Created by Jerry on 2021/8/20.
 */
class SimpleSnaphelper:SnapHelper() {
    companion object{
        private val TAG = "SimpleSnaphelper"
    }

    /**
     * 这个方法会计算第二个参数对应的ItemView当前的坐标与需要对齐的坐标之间的距离。该方法返回一个大小为2的int数组，分别对应x轴和y轴方向上的距离
     */
    override fun calculateDistanceToFinalSnap(layoutManager: RecyclerView.LayoutManager, targetView: View): IntArray? {
        NLog.d(TAG, "calculateDistanceToFinalSnap --->")
        return null
    }

    /**
     * 该方法会找到当前layoutManager上最接近对齐位置的那个view，该view称为SanpView，
     * 对应的position称为SnapPosition。如果返回null，就表示没有需要对齐的View，也就不会做滚动对齐调整。
     */
    override fun findSnapView(layoutManager: RecyclerView.LayoutManager?): View? {
        NLog.d(TAG, "findSnapView --->")
        return null;

    }

    /**
     * 该方法会根据触发Fling操作的速率（参数velocityX和参数velocityY）来找到RecyclerView需要滚动到哪个位置，
     * 该位置对应的ItemView就是那个需要进行对齐的列表项。我们把这个位置称为targetSnapPosition，对应的View称为targetSnapView。如果找不到targetSnapPosition，就返回RecyclerView.NO_POSITION
     */
    override fun findTargetSnapPosition(layoutManager: RecyclerView.LayoutManager?, velocityX: Int, velocityY: Int): Int {
        NLog.d(TAG, "findTargetSnapPosition velocityX: %s, velocityY: %s",velocityX,velocityY)
        return RecyclerView.NO_POSITION
    }
}