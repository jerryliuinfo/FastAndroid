package com.apache.fastandroid.novel.find.rank.view;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.apache.fastandroid.novel.R;
import com.apache.fastandroid.novel.find.bean.RankingList;
import com.apache.fastandroid.novel.find.rank.subrank.SubRankTabsFragment;
import com.apache.fastandroid.novel.support.constant.NovelConstans;
import com.tesla.framework.component.imageloader.ImageLoaderManager;
import com.tesla.framework.support.inject.ViewInject;
import com.tesla.framework.ui.fragment.itemview.ARecycleViewItemViewHolder;



/**
 * Created by 01370340 on 2017/9/24.
 */

public class RankItemView extends ARecycleViewItemViewHolder<RankingList.MaleBean> {

    public static final int LAY_RES = R.layout.novel_item_rank;

    @ViewInject(idStr = "ivIcon")
     ImageView ivIcon;

    @ViewInject(idStr = "tvTitle")
     TextView tvTitle;

    public RankItemView(Activity context, View itemView) {
        super(context, itemView);
    }

    @Override
    public void onBindData(View convertView, final RankingList.MaleBean data, int position) {

        ImageLoaderManager.getInstance().showImage(ivIcon, NovelConstans.IMG_BASE_URL + data.cover,getContext());
        tvTitle.setText(data.title);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubRankTabsFragment.launch(getContext(),data);
            }
        });
    }


}
