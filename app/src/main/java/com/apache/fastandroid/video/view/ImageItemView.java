package com.apache.fastandroid.video.view;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.apache.fastandroid.R;
import com.apache.fastandroid.video.bean.VideoBean;
import com.tesla.framework.component.imageloader.ImageLoaderManager;
import com.tesla.framework.support.inject.ViewInject;
import com.tesla.framework.ui.fragment.itemview.ARecycleViewItemViewHolder;

/**
 * Created by 01370340 on 2017/9/3.
 */

public class ImageItemView  extends ARecycleViewItemViewHolder<VideoBean> {
    public static final int LAY_RES = R.layout.item_video;
    @ViewInject(id = R.id.iv_cover)
    ImageView iv_cover;
    @ViewInject(id = R.id.tv_desc)
    TextView tv_desc;

    @ViewInject(id = R.id.ib_play)
    ImageButton ib_play;

    public ImageItemView(Activity context, View itemView) {
        super(context, itemView);
    }

    @Override
    public void onBindData(View convertView, VideoBean data, int position) {
        if (!TextUtils.isEmpty(data.thumbnail_v2)){
            ImageLoaderManager.getInstance().showImage(ImageLoaderManager.getDefaultOptions(iv_cover,data.thumbnail_v2));
        }
        tv_desc.setText(data.title);
        ib_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}

