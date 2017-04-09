package com.tesla.framework.ui.fragment.itemview.footer;

import com.tesla.framework.network.task.TaskException;
import com.tesla.framework.ui.fragment.ABaseFragment;
import com.tesla.framework.ui.fragment.APagingFragment;

/**
 * Created by jerryliu on 2017/4/4.
 */

public interface OnFootViewListener {
    void onTaskStateChanged(AFooterItemViewHolder<?> footerItemView, ABaseFragment.ABaseTaskState state,
                            TaskException exception, APagingFragment.RefreshMode mode);

    void setFooterViewToRefreshing();
}
