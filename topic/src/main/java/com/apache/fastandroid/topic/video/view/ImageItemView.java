package com.apache.fastandroid.topic.video.view;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.apache.fastandroid.topic.R;
import com.apache.fastandroid.topic.video.action.WifiAction;
import com.apache.fastandroid.topic.video.bean.VideoBean;
import com.tesla.framework.Global;
import com.tesla.framework.common.util.log.NLog;
import com.tesla.framework.component.imageloader.ImageLoaderManager;
import com.tesla.framework.support.action.IAction;
import com.tesla.framework.ui.fragment.itemview.ARecycleViewItemViewHolder;
import com.tesla.framework.ui.widget.ToastUtils;

/**
 * Created by 01370340 on 2017/9/3.
 */

public class ImageItemView  extends ARecycleViewItemViewHolder<VideoBean> {
    public static final int LAY_RES = R.layout.item_video;
    ImageView iv_cover;
    TextView tv_desc;
    ImageButton ib_play;

    public ImageItemView(Activity context, View itemView) {
        super(context, itemView);
        iv_cover = itemView.findViewById(R.id.iv_cover);
        tv_desc = itemView.findViewById(R.id.tv_desc);
        ib_play = itemView.findViewById(R.id.ib_play);
    }

    @Override
    public void onBindData(View convertView, VideoBean data, int position) {
        if (!TextUtils.isEmpty(data.thumbnail_v2)){
            ImageLoaderManager.getInstance().showImage(iv_cover,data.thumbnail_v2,getContext());
        }
        tv_desc.setText(data.title);
        ib_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new IAction(getContext(),new WifiAction(getContext(),null)){
                    @Override
                    public void doAction() {
                        super.doAction();
                        ToastUtils.showToast(Global.getContext(),"开始播放");
                        NLog.d(TAG, "开始播放");
                    }
                }.run();
            }
        });
    }
}

