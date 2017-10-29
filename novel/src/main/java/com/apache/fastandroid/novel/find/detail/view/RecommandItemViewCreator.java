package com.apache.fastandroid.novel.find.detail.view;

import android.app.Activity;
import android.view.View;

import com.apache.fastandroid.novel.find.bean.RecommandBeans;
import com.tesla.framework.ui.fragment.itemview.BaseItemViewCreator;
import com.tesla.framework.ui.fragment.itemview.IITemView;

/**
 * Created by 01370340 on 2017/9/24.
 */

public class RecommandItemViewCreator extends BaseItemViewCreator<RecommandBeans.RecommendBean> {


    public RecommandItemViewCreator(Activity context) {
        super(context);
    }

    @Override
    protected int inflateItemView(int viewType) {
        return RecommandItemView.LAY_RES ;
    }

    @Override
    public IITemView<RecommandBeans.RecommendBean> newItemView(View contentView, int viewType) {
        return new RecommandItemView(getContext(),contentView);
    }
}
