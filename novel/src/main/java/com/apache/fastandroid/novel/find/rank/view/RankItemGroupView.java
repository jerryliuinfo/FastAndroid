package com.apache.fastandroid.novel.find.rank.view;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.apache.fastandroid.novel.R;
import com.apache.fastandroid.novel.find.rank.bean.RankingList;
import com.tesla.framework.support.inject.ViewInject;
import com.tesla.framework.ui.fragment.itemview.ARecycleViewItemViewHolder;

/**
 * Created by 01370340 on 2017/9/24.
 */

public class RankItemGroupView extends ARecycleViewItemViewHolder<RankingList.MaleBean> {

    public static final int LAY_RES = R.layout.novel_item_rank_group;

    @ViewInject(idStr = "tvTitle")
     TextView tvTitle;

    public RankItemGroupView(Activity context, View itemView) {
        super(context, itemView);
    }

    @Override
    public void onBindData(View convertView, RankingList.MaleBean data, int position) {


        tvTitle.setText(data.title);
    }


}
