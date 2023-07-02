package com.apache.fastandroid.demo.recycleview.itemtouch

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView


/**
 * Created by Jerry on 2023/7/2.
 */
class ItemTouchHelperCallback(val iMoveAndSwipeCallback: IMoveAndSwipeCallback) : ItemTouchHelper.Callback() {

    /**
     * 设置拖拽和item滑动的可支持方向
     * @param recyclerView RecyclerView
     * @param viewHolder ViewHolder
     * @return Int
     */
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        //支持上下拖拽
        val dragFlags: Int = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        //item支持左滑
        val swipeFlags: Int = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        return makeMovementFlags(dragFlags, swipeFlags)
    }


    /**
     * 拖拽结束后（手指抬起）会回调的方法
     * @param recyclerView RecyclerView
     * @param viewHolder ViewHolder
     * @param target ViewHolder
     * @return Boolean
     */
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        iMoveAndSwipeCallback.onMove(
            viewHolder.bindingAdapterPosition,
            target.bindingAdapterPosition
        )
        return true
    }

    /**
     * 侧滑回调
     * @param viewHolder ViewHolder
     * @param direction Int
     */
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        iMoveAndSwipeCallback.onSwiped(viewHolder.bindingAdapterPosition)
    }

    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    /**
     * 滑动消失的距离，当滑动小于这个值的时候会删除这个item，否则不会视为删除
     * 返回值作为用户视为拖动的距离
     * @param viewHolder ViewHolder
     * @return Float
     */
    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        return 0.1f
    }


    /**
     * 返回值滑动消失的距离，滑动小于这个值不消失，大于消失
     * @param defaultValue Float
     * @return Float
     */
    override fun getSwipeEscapeVelocity(defaultValue: Float): Float {
        return 5f
    }

    //设置手指离开后ViewHolder的动画时间
    override fun getAnimationDuration(
        recyclerView: RecyclerView,
        animationType: Int,
        animateDx: Float,
        animateDy: Float
    ): Long {
        return 100
    }


    /**
     * 网格型RecyclerView
     * @param viewHolder ViewHolder
     * @return Float
     */
    override fun getMoveThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        return 0.9f    }



    //返回值决定是否有滑动操作
    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }
}