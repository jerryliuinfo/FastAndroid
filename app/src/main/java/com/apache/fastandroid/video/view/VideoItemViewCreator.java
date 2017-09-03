package com.apache.fastandroid.video.view;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apache.fastandroid.video.bean.VideoBean;
import com.tesla.framework.ui.fragment.itemview.IITemView;
import com.tesla.framework.ui.fragment.itemview.IItemViewCreator;

/**
 * Created by 01370340 on 2017/9/3.
 */

public class VideoItemViewCreator implements IItemViewCreator<VideoBean> {
    Activity context;

    public VideoItemViewCreator(Activity context) {
        this.context = context;
    }

    @Override
    public View newContentView(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return inflater.inflate(ImageItemView.LAY_RES,parent,false);
    }

    @Override
    public IITemView<VideoBean> newItemView(View contentView, int viewType) {
        return new ImageItemView(context,contentView);
    }
}
