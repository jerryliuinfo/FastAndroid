package com.apache.fastandroid.pic.view;

import android.app.Activity;
import android.view.View;

import com.apache.fastandroid.support.bean.WallpaperBean;
import com.tesla.framework.ui.fragment.itemview.BaseItemViewCreator;
import com.tesla.framework.ui.fragment.itemview.IITemView;

/**
 * Created by 01370340 on 2017/11/19.
 */

public class WallpaperItemViewCreator extends BaseItemViewCreator<WallpaperBean> {


    public WallpaperItemViewCreator(Activity context) {
        super(context);
    }

    @Override
    protected int inflateItemView(int viewType) {
        return WallpaperItemView.LAY_RES;
    }

    @Override
    public IITemView<WallpaperBean> newItemView(View contentView, int viewType) {
        return new WallpaperItemView(getContext(), contentView);
    }
}
