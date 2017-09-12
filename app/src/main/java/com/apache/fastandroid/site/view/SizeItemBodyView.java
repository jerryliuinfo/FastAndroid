package com.apache.fastandroid.site.view;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.apache.fastandroid.site.bean.SiteItem;
import com.apache.fastandroid.site.bean.Siteable;
import com.tesla.framework.component.imageloader.ImageLoaderManager;
import com.tesla.framework.support.inject.ViewInject;
import com.tesla.framework.ui.fragment.itemview.ARecycleViewItemViewHolder;

/**
 * Created by 01370340 on 2017/9/3.
 */

public class SizeItemBodyView extends ARecycleViewItemViewHolder<Siteable> {

    @ViewInject(idStr = "iv_icon")
    ImageView iv_icon;
    @ViewInject(idStr = "tv_name")
    TextView tv_name;

    public SizeItemBodyView(Activity context, View itemView) {
        super(context, itemView);


    }

    @Override
    public void onBindData(View convertView, Siteable bean, int position) {
        if (bean instanceof SiteItem){
            SiteItem item = (SiteItem) bean;
            if (!TextUtils.isEmpty(item.getAvatar_url())){
                ImageLoaderManager.getInstance().showImage(ImageLoaderManager.getDefaultOptions(iv_icon, item.getAvatar_url()));

            }
            tv_name.setText(item.getName());
        }

    }
}
