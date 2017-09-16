package com.apache.fastandroid.topic;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apache.fastandroid.R;
import com.apache.fastandroid.artemis.base.BaseFragment;
import com.apache.fastandroid.artemis.comBridge.ModularizationDelegate;
import com.apache.fastandroid.artemis.support.bean.User;
import com.apache.fastandroid.topic.bean.TopicBean;
import com.tesla.framework.common.util.log.NLog;
import com.tesla.framework.component.imageloader.ImageLoaderManager;
import com.tesla.framework.support.inject.ViewInject;
import com.tesla.framework.ui.activity.FragmentArgs;
import com.tesla.framework.ui.activity.FragmentContainerActivity;
import com.tesla.framework.ui.widget.CircleImageView;

/**
 * Created by 01370340 on 2017/9/14.
 */

public class TopicDetailFragment extends BaseFragment {



    @ViewInject(idStr = "avatar")
    CircleImageView iv_avatar;

    @ViewInject(idStr = "lay_header")
    private View mHeaderView;

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

    @ViewInject(idStr = "stub_not_login")
    ViewStub stub_not_login;
    View mBottomNotLogin;

    @ViewInject(idStr = "stub_can_reply")
    ViewStub stub_can_reply;
    View mBottomCanReply;




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
        //NLog.d(TAG, "layoutInit lay_header = %s", lay_header);
        stub_not_login = (ViewStub) findViewById(R.id.stub_not_login);
        stub_can_reply = (ViewStub) findViewById(R.id.stub_can_reply);
        if (savedInstanceSate == null){
            mTopicBean = (TopicBean) getArguments().getSerializable("topic");
        }else {
            mTopicBean = (TopicBean) savedInstanceSate.getSerializable("topic");
        }
        showUserInfo();


    }

    @Override
    public void onResume() {
        super.onResume();
        showBottom();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("topic", mTopicBean);
    }

    private void showUserInfo(){

        View mHeaderView = findViewById(R.id.layout_content_header);
        NLog.d(TAG, "showUserInfo mHeaderView = %s", mHeaderView);

        User user = mTopicBean.user;
        ((TextView)mHeaderView.findViewById(R.id.username)).setText(user.getName());

        ((TextView)mHeaderView.findViewById(R.id.time)).setText(mTopicBean.updated_at);

        ((TextView)mHeaderView.findViewById(R.id.title)).setText(mTopicBean.title);

        if (!TextUtils.isEmpty(user.getAvatar_url())){
            ImageView iv_avatar = (ImageView) findViewById(R.id.avatar);
            ImageLoaderManager.getInstance().showImage(ImageLoaderManager.getDefaultOptions(iv_avatar,user.getAvatar_url()));
        }
        iv_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ModularizationDelegate.getInstance().runStaticAction("com.apache.fastandroid:userCenter:startLoginActivity",null,null,new Object[]{getActivity()});
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        TextView tv_reply_count = (TextView) findViewById(R.id.reply_count);
        tv_reply_count.setText("共收到 " + mTopicBean.replies_count + "条回复");
    }

    private void showBottom(){
        try {
            Bundle result = ModularizationDelegate.getInstance().getData("com.apache.fastandroid:userCenter:isLogined",null,new Object[]{});
            if (result != null && result.containsKey("result")){
                boolean isLogined = result.getBoolean("result");
                if (isLogined){
                    if (mBottomCanReply == null){
                        mBottomCanReply = stub_can_reply.inflate();
                    }
                    stub_can_reply.setVisibility(View.VISIBLE);
                    stub_not_login.setVisibility(View.GONE);

                }else {
                    if (mBottomNotLogin == null){
                        mBottomNotLogin = stub_not_login.inflate();
                    }
                    stub_not_login.setVisibility(View.VISIBLE);
                    stub_can_reply.setVisibility(View.GONE);
                    Button login = (Button) mBottomNotLogin.findViewById(R.id.login);
                    login.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                ModularizationDelegate.getInstance().runStaticAction("com.apache.fastandroid:userCenter:startLoginActivity",null,null,new Object[]{getActivity(),true});
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
