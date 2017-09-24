package com.apache.fastandroid.novel.recommand.view;

import android.app.Activity;
import android.view.View;

import com.apache.fastandroid.novel.recommand.bean.RecommendBeans;
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
