package com.fast.android.novel.find.view;

import android.app.Activity;
import android.view.View;

import com.fast.android.novel.find.bean.FindBean;
import com.tesla.framework.ui.fragment.itemview.BaseItemViewCreator;
import com.tesla.framework.ui.fragment.itemview.IITemView;

/**
 * Created by 01370340 on 2017/9/24.
 */

public class CommunityItemViewCreator extends BaseItemViewCreator<FindBean> {
    public CommunityItemViewCreator(Activity context) {
        super(context);
    }

    @Override
    protected int inflateItemView() {
        return CommunityItemView.LAY_RES;
    }

    @Override
    public IITemView<FindBean> newItemView(View contentView, int viewType) {
        return new CommunityItemView(getContext(),contentView);
    }
}
