package com.apache.fastandroid.topic.news.view;

import android.app.Activity;
import android.view.View;

import com.apache.fastandroid.topic.support.bean.NewsSummary;
import com.tesla.framework.ui.fragment.itemview.BaseItemViewCreator;
import com.tesla.framework.ui.fragment.itemview.IITemView;

/**
 * Created by 01370340 on 2017/11/23.
 */

public class MainNewsItemViewCreator extends BaseItemViewCreator {
    public MainNewsItemViewCreator(Activity context) {
        super(context);
    }

    @Override
    public IITemView newItemView(View contentView, int viewType) {
        if (NewsSummary.TYPE_NORMAL == viewType){
            return new MainNewsItemView(getContext(),contentView);
        }
        return new MainNewsItemPhotoView(getContext(),contentView);
    }

    @Override
    protected int inflateItemView(int viewType) {
        if (NewsSummary.TYPE_NORMAL == viewType){
            return MainNewsItemView.LAY_RES;
        }
        return MainNewsItemPhotoView.LAY_RES;
    }
}
