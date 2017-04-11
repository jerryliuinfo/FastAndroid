package com.apache.fastandroid.ui.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by jerryliu on 2017/4/11.
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration{
    int mSpace;

    public SpaceItemDecoration(int mSpace) {
        this.mSpace = mSpace;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.left = mSpace;
        outRect.top = mSpace;
        if (parent.getChildLayoutPosition(view) % 2 ==1){
            outRect.right = mSpace;
        }
    }
}
