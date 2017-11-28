package com.tesla.framework.ui.fragment.adpater.drag;

/**
 * Created by 01370340 on 2017/11/24.
 */

public interface OnItemMoveListener {

    boolean onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}
