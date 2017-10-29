package com.apache.fastandroid.novel.bookshelf;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.apache.fastandroid.novel.NovelHomeTabsFragment;
import com.apache.fastandroid.novel.R;
import com.tesla.framework.network.task.TaskException;
import com.tesla.framework.support.inject.ViewInject;
import com.tesla.framework.ui.fragment.ABaseFragment;
import com.tesla.framework.ui.fragment.APagingFragment;
import com.tesla.framework.ui.fragment.itemview.footer.AFooterItemViewHolder;

import java.io.Serializable;

/**
 * Created by 01370340 on 2017/10/28.
 */

public class BookSelfFootView <T extends Serializable> extends AFooterItemViewHolder<T> {

    public static final int LAY_RES = R.layout.foot_view_shelf;

    @ViewInject(idStr = "btn_add")
    TextView btn_add;

    public BookSelfFootView(Activity context, View itemView, OnFooterViewCallback callback) {
        super(context, itemView, callback);
    }

    @Override
    public void onTaskStateChanged(AFooterItemViewHolder<?> footerItemView, ABaseFragment.ABaseTaskState state, TaskException exception, APagingFragment.RefreshMode mode) {

    }

    @Override
    public void setFooterViewToRefreshing() {

    }

    @Override
    public void onBindView(View convertView) {
        super.onBindView(convertView);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity context = (FragmentActivity) getContext();
                Fragment fragment = context.getSupportFragmentManager().findFragmentByTag("MainFragment");
                if (fragment != null && fragment instanceof NovelHomeTabsFragment){
                    NovelHomeTabsFragment novelHomeTabsFragment = (NovelHomeTabsFragment) fragment;
                    novelHomeTabsFragment.selectFindFragment();
                }
            }
        });
    }

    @Override
    public void onBindData(View convertView, T data, int position) {

    }

}
