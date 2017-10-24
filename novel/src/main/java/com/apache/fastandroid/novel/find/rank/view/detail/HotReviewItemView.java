package com.apache.fastandroid.novel.find.rank.view.detail;

import android.app.Activity;
import android.view.View;

import com.apache.fastandroid.novel.R;
import com.apache.fastandroid.novel.find.bean.HotReviewBean;
import com.tesla.framework.ui.fragment.itemview.ARecycleViewItemViewHolder;


/**
 * Created by 01370340 on 2017/9/24.
 */

public class HotReviewItemView extends ARecycleViewItemViewHolder<HotReviewBean.Reviews> {

    public static final int LAY_RES = R.layout.novel_item_book_detail_recommend_book_list;

    public HotReviewItemView(Activity context, View itemView) {
        super(context, itemView);
    }


    @Override
    public void onBindData(View convertView, HotReviewBean.Reviews data, int position) {

    }

}
