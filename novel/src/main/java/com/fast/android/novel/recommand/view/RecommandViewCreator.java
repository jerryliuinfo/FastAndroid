package com.fast.android.novel.recommand.view;

import android.app.Activity;
import android.view.View;

import com.fast.android.novel.R;
import com.fast.android.novel.recommand.bean.RecommendBeans;
import com.tesla.framework.ui.fragment.itemview.BaseItemViewCreator;
import com.tesla.framework.ui.fragment.itemview.IITemView;

/**
 * Created by 01370340 on 2017/9/24.
 */

public class RecommandViewCreator extends BaseItemViewCreator<RecommendBeans.RecommendBook> {
    public RecommandViewCreator(Activity context) {
        super(context);
    }

    @Override
    protected int inflateItemView() {
        return R.layout.novel_item_recommand;
    }

    @Override
    public IITemView<RecommendBeans.RecommendBook> newItemView(View contentView, int viewType) {
        return new RecomandItemView(getContext(),contentView);
    }
}
