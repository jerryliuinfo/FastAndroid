package com.apache.fastandroid.news.view;

import android.app.Activity;
import android.view.View;

import com.apache.fastandroid.R;
import com.apache.fastandroid.support.bean.NewsSummary;
import com.tesla.framework.ui.fragment.itemview.footer.BaisicFooterVIew;

/**
 * Created by 01370340 on 2017/11/29.
 */

public class MainNewsFootView extends BaisicFooterVIew<NewsSummary> {
    public static final int LAY_RES = R.layout.layout_main_news_footview;



    public MainNewsFootView(Activity context, View itemView, OnFooterViewCallback callback) {
        super(context, itemView, callback);
    }

   /* @Override
    protected String loadingText() {
        return "正在拼命加载中...";
    }*/
}
