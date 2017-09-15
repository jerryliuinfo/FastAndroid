package com.apache.fastandroid.topic;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apache.fastandroid.R;
import com.apache.fastandroid.artemis.base.BaseFragment;
import com.apache.fastandroid.artemis.support.bean.User;
import com.apache.fastandroid.support.utils.TimeUtil;
import com.apache.fastandroid.topic.bean.TopicBean;
import com.tesla.framework.component.imageloader.ImageLoaderManager;
import com.tesla.framework.support.inject.ViewInject;
import com.tesla.framework.ui.activity.FragmentArgs;
import com.tesla.framework.ui.activity.FragmentContainerActivity;
import com.tesla.framework.ui.widget.CircleImageView;

/**
 * Created by 01370340 on 2017/9/14.
 */

public class TopicDetailFragment extends BaseFragment {

    @ViewInject(idStr = "activity_topic_detail")
    RelativeLayout activityTopicDetail;

    @ViewInject(idStr = "toolbar")
    Toolbar toolbar;

    @ViewInject(idStr = "panel")
    RelativeLayout panel;

    @ViewInject(idStr = "avatar")
    CircleImageView iv_avatar;

    @ViewInject(idStr = "username")
    TextView tv_username;

    @ViewInject(idStr = "point")
    TextView tv_point;

    @ViewInject(idStr = "node_name")
    TextView tv_nodeName;

    @ViewInject(idStr = "time")
    TextView tv_time;

    @ViewInject(idStr = "title")
    TextView tv_title;

    @ViewInject(idStr = "webview_container")
    FrameLayout webviewContainer;

    @ViewInject(idStr = "reply_count")
    TextView tv_replyCount;

    @ViewInject(idStr = "reply_list")
    RecyclerView replyList;

    @ViewInject(idStr = "reply_list")
    RelativeLayout needLogin;

    @ViewInject(idStr = "can_reply")
    RelativeLayout canReply;

    @ViewInject(idStr = "my_reply")
    EditText et_myReply;




    private TopicBean mTopicBean;

    public static void start(Activity from,TopicBean topicBean) {
        FragmentArgs args = new FragmentArgs();
        args.add("topic", topicBean);
        FragmentContainerActivity.launch(from,TopicDetailFragment.class,args);
    }



    @Override
    public int inflateContentView() {
        return R.layout.fragment_topic_content;
    }


    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);

        setToolbarTitle("话题");

        if (savedInstanceSate == null){
            mTopicBean = (TopicBean) getArguments().getSerializable("topic");
        }else {
            mTopicBean = (TopicBean) savedInstanceSate.getSerializable("topic");
        }
        showUserInfo();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("topic", mTopicBean);
    }

    private void showUserInfo(){
        User user = mTopicBean.user;
        tv_username.setText(user.getName());
        tv_time.setText(TimeUtil.computePastTime(mTopicBean.updated_at));
        tv_title.setText(mTopicBean.title);
        tv_replyCount.setText("共收到 " + mTopicBean.replies_count + "条回复");
        if (!TextUtils.isEmpty(user.getAvatar_url())){
            ImageLoaderManager.getInstance().showImage(ImageLoaderManager.getDefaultOptions(iv_avatar,user.getAvatar_url()));
        }
        iv_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
