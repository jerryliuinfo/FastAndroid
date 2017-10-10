package com.apache.fastandroid.novel.find.rank.view;

import android.app.Activity;
import android.view.View;

import com.apache.fastandroid.novel.find.rank.bean.RankingList;
import com.tesla.framework.ui.fragment.itemview.BaseItemViewCreator;
import com.tesla.framework.ui.fragment.itemview.IITemView;

/**
 * Created by 01370340 on 2017/9/24.
 */

public class RankItemViewCreator extends BaseItemViewCreator<RankingList.MaleBean> {
    //用于区分"男生","女生"的item
    public static final int TYPE_GROUP = 100;

    public RankItemViewCreator(Activity context) {
        super(context);
    }

    @Override
    protected int inflateItemView(int viewType) {
        if (viewType == TYPE_GROUP){
            return RankItemGroupView.LAY_RES ;
        }else {
            return RankItemView.LAY_RES;
        }
    }

    @Override
    public IITemView<RankingList.MaleBean> newItemView(View contentView, int viewType) {
        if (viewType == TYPE_GROUP){
            return new RankItemGroupView(getContext(), contentView);
        }else {
            return new RankItemView(getContext(), contentView);
        }
    }
}
