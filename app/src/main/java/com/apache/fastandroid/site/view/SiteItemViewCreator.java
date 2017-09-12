package com.apache.fastandroid.site.view;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apache.fastandroid.R;
import com.apache.fastandroid.site.bean.Siteable;
import com.tesla.framework.ui.fragment.itemview.IITemView;
import com.tesla.framework.ui.fragment.itemview.IItemViewCreator;

/**
 * Created by 01370340 on 2017/9/3.
 */

public class SiteItemViewCreator implements IItemViewCreator<Siteable> {
    Activity context;
    public SiteItemViewCreator(Activity context){
        this.context = context;
    }

    @Override
    public View newContentView(LayoutInflater inflater, ViewGroup parent, int viewType) {
        if (viewType == 100){
            return inflater.inflate(R.layout.item_site_header,parent,false);
        }else {
            return inflater.inflate(R.layout.item_site_body,parent,false);
        }
    }

    @Override
    public IITemView<Siteable> newItemView(View contentView, int viewType) {
        if (viewType == 100){
            return new SizeItemHeaderView(context,contentView);
        }else {
            return new SizeItemBodyView(context,contentView);
        }


    }
}