package com.apache.fastandroid.novel.find.detail.view;

import android.app.Activity;
import android.view.View;

import com.apache.fastandroid.novel.find.bean.HotReviewBean;
import com.tesla.framework.ui.fragment.itemview.BaseItemViewCreator;
import com.tesla.framework.ui.fragment.itemview.IITemView;

/**
 * Created by 01370340 on 2017/9/24.
 */

public class HotReviewItemViewCreator extends BaseItemViewCreator<HotReviewBean.Reviews> {


    public HotReviewItemViewCreator(Activity context) {
        super(context);
    }

    @Override
    protected int inflateItemView(int viewType) {
        return HotReviewItemView.LAY_RES ;
    }

    @Override
    public IITemView<HotReviewBean.Reviews> newItemView(View contentView, int viewType) {
        return new HotReviewItemView(getContext(),contentView);
    }

}
