package com.fast.android.novel.recommand.view;

import android.app.Activity;
import android.view.View;

import com.fast.android.novel.recommand.bean.RecommendBeans;
import com.tesla.framework.ui.fragment.itemview.ARecycleViewItemViewHolder;

/**
 * Created by 01370340 on 2017/9/24.
 */

public class RecomandItemView extends ARecycleViewItemViewHolder<RecommendBeans.RecommendBook> {

    public RecomandItemView(Activity context, View itemView) {
        super(context, itemView);
    }

    @Override
    public void onBindData(View convertView, RecommendBeans.RecommendBook data, int position) {

    }
}
