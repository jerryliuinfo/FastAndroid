package com.apache.fastandroid.test.view;

import android.app.Activity;
import android.view.View;

import com.apache.fastandroid.news.bean.NewsBean;
import com.tesla.framework.ui.fragment.itemview.BaseItemViewCreator;
import com.tesla.framework.ui.fragment.itemview.IITemView;

/**
 * Created by 01370340 on 2017/11/30.
 */

public class TestItemViewCreator extends BaseItemViewCreator<NewsBean>{
    public TestItemViewCreator(Activity context) {
        super(context);
    }

    @Override
    public IITemView<NewsBean> newItemView(View contentView, int viewType) {
        return new TestItemView(getContext(),contentView);
    }

    @Override
    protected int inflateItemView(int viewType) {
        return TestItemView.LAY_RES;
    }
}
