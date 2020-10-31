/*
 * Copyright (c) 2016 咖枯 <kaku201313@163.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.tesla.framework.ui.fragment.adpater.drag;

import android.graphics.Color;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;

public class ItemDragHelperCallback extends ItemTouchHelper.Callback {

    private OnItemMoveListener mOnItemMoveListener;

    private boolean mIsLongPressEnabled = true;

    public void setLongPressEnabled(boolean longPressEnabled) {
        mIsLongPressEnabled = longPressEnabled;
    }



    public ItemDragHelperCallback(OnItemMoveListener onItemMoveListener) {
        mOnItemMoveListener = onItemMoveListener;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return mIsLongPressEnabled;
    }

    /**
     * getMovementFlags用于设置是否处理拖拽事件和滑动事件，以及拖拽和滑动操作的方向，比如如果是列表类型的RecyclerView，
     * 拖拽只有UP、DOWN两个方向，而如果是网格类型的则有UP、DOWN、LEFT、RIGHT四个方向：
     * @return
     */


    /**
     * getMovementFlags用于设置是否处理拖拽事件和滑动事件，以及拖拽和滑动操作的方向，比如如果是列表类型的RecyclerView，
     * 拖拽只有UP、DOWN两个方向，而如果是网格类型的则有UP、DOWN、LEFT、RIGHT四个方向：
     * @param recyclerView
     * @param viewHolder
     * @return
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager || layoutManager instanceof StaggeredGridLayoutManager) {
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        } else {
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        }
        int swipeFlags = 0;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    /**
     * 长按item的时候就会进入拖拽并在拖拽过程中不断回调onMove()方法
     * @param recyclerView
     * @param viewHolder
     * @param target
     * @return
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return !isDifferentItemViewType(viewHolder, target) &&
                mOnItemMoveListener.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());

    }

    private boolean isDifferentItemViewType(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return viewHolder.getItemViewType() != target.getItemViewType();
    }



    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }

    /**
     * 拖拽的Item在拖拽的过程中背景颜色加深
     * @param viewHolder
     * @param actionState
     */
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE){
            viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
        }
    }

    /**
     * 拖拽完成的时候
     * @param recyclerView
     * @param viewHolder
     */
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        viewHolder.itemView.setBackgroundColor(0);
    }


}
