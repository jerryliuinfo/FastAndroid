package com.apache.fastandroid.news.view;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.apache.fastandroid.artemis.support.bean.User;
import com.apache.fastandroid.artemis.util.IntentUtil;
import com.apache.fastandroid.artemis.util.TimeUtil;
import com.apache.fastandroid.artemis.util.UrlUtil;
import com.apache.fastandroid.news.bean.NewsBean;
import com.tesla.framework.component.imageloader.ImageLoaderManager;
import com.tesla.framework.support.inject.ViewInject;
import com.tesla.framework.ui.fragment.itemview.ARecycleViewItemViewHolder;

/**
 * Created by 01370340 on 2017/9/3.
 */

public class InformationItemView extends ARecycleViewItemViewHolder<NewsBean> {

    @ViewInject(idStr = "username")
    TextView username;
    @ViewInject(idStr = "node_name")
    TextView node_name;
    @ViewInject(idStr = "time")
    TextView time;
    @ViewInject(idStr = "title")
    TextView title;
    @ViewInject(idStr = "host_name")
    TextView host_name;
    @ViewInject(idStr = "avatar")
    ImageView avatar;
    public InformationItemView(Activity context, View itemView) {
        super(context, itemView);
    }

    @Override
    public void onBindData(View convertView, final NewsBean bean, int position) {
        final User user = bean.getUser();
        username.setText(user.getLogin());
        node_name.setText(bean.getNode_name());
        time.setText(TimeUtil.computePastTime(bean.getUpdated_at()));
        title.setText(bean.getTitle());
        host_name.setText(UrlUtil.getHost(bean.getAddress()));

        String url = user.getAvatar_url();
        if (!TextUtils.isEmpty(url) && url.contains("diycode")) {   // 添加判断，防止替换掉其他网站掉图片
            url = url.replace("large_avatar", "avatar");
        }
        if (!TextUtils.isEmpty(url)) {
            ImageLoaderManager.getInstance().showImage(avatar,url,getContext());
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtil.openUrl(v.getContext(),bean.getAddress());
            }
        });
    }
}
