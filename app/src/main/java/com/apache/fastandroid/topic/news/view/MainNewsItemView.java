package com.apache.fastandroid.topic.news.view;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.apache.fastandroid.R;
import com.apache.fastandroid.topic.support.bean.NewsSummary;
import com.tesla.framework.component.imageloader.ImageLoaderManager;
import com.tesla.framework.support.inject.ViewInject;
import com.tesla.framework.ui.fragment.itemview.ARecycleViewItemViewHolder;

/**
 * Created by 01370340 on 2017/11/23.
 */

public class MainNewsItemView extends ARecycleViewItemViewHolder<NewsSummary> {

    public static final int LAY_RES = R.layout.item_news;

    @ViewInject(idStr = "news_summary_title_tv")
    TextView news_summary_title_tv;

    @ViewInject(idStr = "news_summary_ptime_tv")
    TextView news_summary_ptime_tv;

    @ViewInject(idStr = "news_summary_digest_tv")
    TextView news_summary_digest_tv;


    @ViewInject(idStr = "news_summary_photo_iv")
    ImageView news_summary_photo_iv;




    public MainNewsItemView(Activity context, View itemView) {
        super(context, itemView);
    }

    @Override
    public void onBindData(View convertView, NewsSummary newsSummary, int position) {
        String title = newsSummary.ltitle;
        if (title == null) {
            title = newsSummary.title;
        }
        news_summary_title_tv.setText(title);
        news_summary_ptime_tv.setText(newsSummary.ptime);
        news_summary_digest_tv.setText(newsSummary.digest);
        ImageLoaderManager.getInstance().showImage(news_summary_photo_iv,newsSummary.imgsrc,getContext());
    }
}
