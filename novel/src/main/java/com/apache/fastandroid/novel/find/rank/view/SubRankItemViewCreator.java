package com.apache.fastandroid.novel.find.rank.view;

import android.app.Activity;
import android.view.View;

import com.apache.fastandroid.novel.find.bean.BookBean;
import com.tesla.framework.ui.fragment.itemview.BaseItemViewCreator;
import com.tesla.framework.ui.fragment.itemview.IITemView;

/**
 * Created by 01370340 on 2017/10/10.
 */

public class SubRankItemViewCreator extends BaseItemViewCreator<BookBean> {
    public SubRankItemViewCreator(Activity context) {
        super(context);
    }

    @Override
    public IITemView<BookBean> newItemView(View contentView, int viewType) {
        return new SubRankItemView(getContext(),contentView);
    }

    @Override
    protected int inflateItemView(int viewType) {
        return SubRankItemView.LAY_RES;
    }
}
