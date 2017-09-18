package com.apache.fastandroid.topic.view;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.apache.fastandroid.artemis.support.bean.User;
import com.apache.fastandroid.artemis.util.TimeUtil;
import com.apache.fastandroid.topic.R;
import com.apache.fastandroid.topic.bean.TopicReplyBean;
import com.tesla.framework.component.imageloader.ImageLoaderManager;
import com.tesla.framework.support.inject.ViewInject;
import com.tesla.framework.ui.fragment.itemview.ARecycleViewItemViewHolder;

/**
 * Created by 01370340 on 2017/9/3.
 */

public class TopicItemReplyView extends ARecycleViewItemViewHolder<TopicReplyBean> {
    public static final int LAYOUT_RES = R.layout.item_topic_reply;

    @ViewInject(idStr = "username")
    TextView username;

    @ViewInject(idStr = "time")
    TextView time;

    @ViewInject(idStr = "content")
    TextView content;

    @ViewInject(idStr = "avatar")
    ImageView avatar;

    public TopicItemReplyView(Activity context, View itemView) {
        super(context, itemView);

        username = (TextView) itemView.findViewById(R.id.username);
        time = (TextView) itemView.findViewById(R.id.time);
        content = (TextView) itemView.findViewById(R.id.content);
        avatar = (ImageView) itemView.findViewById(R.id.avatar);
    }

    @Override
    public void onBindData(View convertView, TopicReplyBean bean, int position) {
        /*final User user = bean.getUser();
        holder.setText(R.id.username, user.getLogin());
        holder.setText(R.id.time, TimeUtil.computePastTime(bean.getUpdated_at()));

        ImageView avatar = holder.get(R.id.avatar);
        ImageUtils.loadImage(mContext, user.getAvatar_url(), avatar);
        TextView content = holder.get(R.id.content);
        // TODO 评论区代码问题
        content.setText(Html.fromHtml(HtmlUtil.removeP(bean.getBody_html()), new GlideImageGetter(mContext, content), null));

        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, UserActivity.class);
                intent.putExtra(UserActivity.USER, user);
                mContext.startActivity(intent);
            }
        }, R.id.avatar, R.id.username);*/

        final User user = bean.getUser();
        username.setText(user.getLogin());
        time.setText(TimeUtil.computePastTime(bean.getUpdated_at()));
        //content.setText(Html.fromHtml(HtmlUtil.removeP(bean.getBody_html()), new GlideImageGetter(mContext, content), null));


        String url = user.getAvatar_url();
        if (!TextUtils.isEmpty(url) && url.contains("diycode")) {   // 添加判断，防止替换掉其他网站掉图片
            url = url.replace("large_avatar", "avatar");
        }
        if (!TextUtils.isEmpty(url)) {
            ImageLoaderManager.getInstance().showImage(ImageLoaderManager.getDefaultOptions(avatar, url));
        }



    }


}
