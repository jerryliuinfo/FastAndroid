package com.apache.fastandroid.news.view;

import android.app.Activity;
import android.view.View;

import com.apache.fastandroid.news.bean.NewsBean;
import com.apache.fastandroid.topic.R;
import com.tesla.framework.ui.fragment.itemview.BaseItemViewCreator;
import com.tesla.framework.ui.fragment.itemview.IITemView;

/**
 * Created by 01370340 on 2017/9/3.
 */

public class NewsItemViewCreator extends BaseItemViewCreator<NewsBean> {
    public NewsItemViewCreator(Activity context) {
        super(context);
    }

    @Override
    protected int inflateItemView(int viewType) {
        return R.layout.news_item_news;
    }

    @Override
    public IITemView<NewsBean> newItemView(View contentView, int viewType) {
        return new NewsItemView(getContext(),contentView);
    }


}