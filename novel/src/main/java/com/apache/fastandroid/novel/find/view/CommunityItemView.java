package com.apache.fastandroid.novel.find.view;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.apache.fastandroid.novel.R;
import com.apache.fastandroid.novel.find.bean.FindBean;
import com.apache.fastandroid.novel.find.rank.TopRankListFragment;
import com.tesla.framework.support.inject.ViewInject;
import com.tesla.framework.ui.fragment.itemview.ARecycleViewItemViewHolder;

/**
 * Created by 01370340 on 2017/9/24.
 */

public class CommunityItemView extends ARecycleViewItemViewHolder<FindBean> {

    public static final int LAY_RES = R.layout.novel_item_community;

    @ViewInject(idStr = "ivIcon")
     ImageView ivIcon;

    @ViewInject(idStr = "tvTitle")
     TextView tvTitle;

    public CommunityItemView(Activity context, View itemView) {
        super(context, itemView);
    }

    @Override
    public void onBindData(View convertView, FindBean data, int position) {
        ivIcon.setImageResource(data.getIconResId());
        tvTitle.setText(data.getTitle());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TopRankListFragment.launch(getContext());
            }
        });
    }
}