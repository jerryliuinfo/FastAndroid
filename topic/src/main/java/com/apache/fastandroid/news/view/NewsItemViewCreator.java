package com.apache.fastandroid.news.view;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apache.fastandroid.news.bean.NewsBean;
import com.apache.fastandroid.topic.R;
import com.tesla.framework.ui.fragment.itemview.IITemView;
import com.tesla.framework.ui.fragment.itemview.IItemViewCreator;

/**
 * Created by 01370340 on 2017/9/3.
 */

public class NewsItemViewCreator implements IItemViewCreator<NewsBean> {

    Activity context;
    public NewsItemViewCreator(Activity context){
        this.context = context;
    }
    @Override
    public View newContentView(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return inflater.inflate(R.layout.news_item_news,parent,false);
    }

    @Override
    public IITemView<NewsBean> newItemView(View contentView, int viewType) {
        return new NewsItemView(context,contentView);
    }
}