package com.apache.fastandroid.topic.view;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.apache.fastandroid.artemis.support.bean.User;
import com.apache.fastandroid.artemis.util.TimeUtil;
import com.apache.fastandroid.topic.R;
import com.apache.fastandroid.topic.TopicDetailActivity;
import com.apache.fastandroid.topic.bean.TopicBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.tesla.framework.common.util.log.NLog;
import com.tesla.framework.ui.fragment.itemview.ARecycleViewItemViewHolder;

/**
 * Created by 01370340 on 2017/9/3.
 */

public class TopicItemView  extends ARecycleViewItemViewHolder<TopicBean> {
    public static final int LAYOUT_RES = R.layout.topic_item_topic;

    TextView username;
    TextView node_name;
    TextView time;
    TextView title;
    TextView state;
    ImageView avatar;

    public TopicItemView(Activity context, View itemView) {
        super(context, itemView);

        username = (TextView) itemView.findViewById(R.id.username);
        node_name = (TextView) itemView.findViewById(R.id.node_name);
        time = (TextView) itemView.findViewById(R.id.time);
        title = (TextView) itemView.findViewById(R.id.title);
        state = (TextView) itemView.findViewById(R.id.state);
        avatar = (ImageView) itemView.findViewById(R.id.avatar);
    }

    @Override
    public void onBindData(View convertView, final TopicBean bean, int position) {
        final User user = bean.user;
        username.setText(user.getLogin());
        node_name.setText(bean.node_name);
        time.setText(TimeUtil.computePastTime(bean.updated_at));
        title.setText(bean.title);
        state.setText("评论 " + bean.replies_count);


        String url = user.getAvatar_url();
        if (!TextUtils.isEmpty(url) && url.contains("diycode")) {   // 添加判断，防止替换掉其他网站掉图片
            url = url.replace("large_avatar", "avatar");
        }
        url = "https://timgsa.baidu.com/timg?image=&quality=80&size=b9999_10000&sec=1513606616748&di=bcc33cd71d6cdb3734cde2216b8e984b&imgtype=0&src=http%3A%2F%2Fwww.86wan.com%2Fuploads%2Fallimg%2F1308%2F2111-130P6110Q0.jpg";

        //ImageLoaderManager.getInstance().showImage(avatar,url,getContext());
        //Glide.with(getContext()).load(url).into(avatar);
        final String finalUrl = url;
        Glide.with(getContext()).load(url).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean
                    isFirstResource) {
                NLog.d(NLog.TAG, "onException url = %s, Exception = %s", finalUrl,e);
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                return false;
            }
        }).into(avatar);



       convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TopicDetailActivity.launch(getContext(),bean);
            }
        });

    }
}
