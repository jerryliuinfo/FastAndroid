package com.tesla.framework.component.divider

import android.graphics.Rect
import android.view.View
import androidx.annotation.Px
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Jerry on 2023/7/2.
 */

/**
 * A [RecyclerView.ItemDecoration] which adds the given `spacing` to the bottom of any view,
 * unless it is the last item.
 */
class BottomSpacingItemDecoration(
    @Px private val spacing: Int
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val lastItem = parent.getChildAdapterPosition(view) == state.itemCount - 1
        outRect.bottom = if (!lastItem) spacing else spacing
    }
}