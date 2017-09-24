package com.apache.fastandroid.topic;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.apache.fastandroid.artemis.base.BaseFragment;
import com.apache.fastandroid.artemis.comBridge.ModularizationDelegate;
import com.apache.fastandroid.artemis.comBridge.ModuleConstans;
import com.apache.fastandroid.artemis.rx.DefaultCallback;
import com.apache.fastandroid.artemis.support.bean.User;
import com.apache.fastandroid.artemis.util.TimeUtil;
import com.apache.fastandroid.topic.bean.TopicBean;
import com.apache.fastandroid.topic.bean.TopicContent;
import com.apache.fastandroid.topic.sdk.TopicSDK;
import com.apache.fastandroid.widget.webview.GcsMarkdownViewClient;
import com.apache.fastandroid.widget.webview.MarkdownView;
import com.apache.fastandroid.widget.webview.WebImageListener;
import com.tesla.framework.common.util.log.NLog;
import com.tesla.framework.component.imageloader.ImageLoaderManager;
import com.tesla.framework.support.inject.ViewInject;
import com.tesla.framework.ui.activity.BaseActivity;
import com.tesla.framework.ui.activity.FragmentArgs;
import com.tesla.framework.ui.activity.FragmentContainerActivity;
import com.tesla.framework.ui.fragment.ABaseFragment;
import com.tesla.framework.ui.widget.CircleImageView;

import rx.Observable;

/**
 * Created by 01370340 on 2017/9/14.
 */

public class TopicDetailFragment extends BaseFragment {
    @ViewInject(idStr = "avatar")
    CircleImageView iv_avatar;

    @ViewInject(idStr = "stub_not_login")
    ViewStub stub_not_login;
    View mNotLoginView;

    @ViewInject(idStr = "stub_can_reply")
    ViewStub stub_can_reply;
    View mReplyView;

    private MarkdownView mMarkdownView;
    private GcsMarkdownViewClient mWebViewClient;


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
        initMarkdownView();

        Fragment fragment = TopicReplyListFragment.newInstance(mTopicBean);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.reply_list_container,fragment).commit();


    }

    @Override
    public void onResume() {
        super.onResume();
        showBottom();
    }

    @Override
    public void requestData() {
        super.requestData();
        loadTopicDetail();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("topic", mTopicBean);
    }

    private void showUserInfo(){
        View mHeaderView = findViewById(R.id.layout_content_header);
        NLog.d(ABaseFragment.TAG, "showUserInfo mHeaderView = %s", mHeaderView);
        User user = mTopicBean.user;
        ((TextView)mHeaderView.findViewById(R.id.username)).setText(user.getName());
        ((TextView)mHeaderView.findViewById(R.id.time)).setText(TimeUtil.computePastTime(mTopicBean.updated_at));
        ((TextView)mHeaderView.findViewById(R.id.title)).setText(mTopicBean.title);
        if (!TextUtils.isEmpty(user.getAvatar_url())){
            ImageView iv_avatar = (ImageView) findViewById(R.id.avatar);
            ImageLoaderManager.getInstance().showImage(ImageLoaderManager.getDefaultOptions(iv_avatar,user.getAvatar_url()));
        }
        iv_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ModularizationDelegate.getInstance().runStaticAction(ModuleConstans.MODULE_USER_CENTER_NAME+":userCenter:startLoginActivity",null,null,new Object[]{getActivity()});
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
            Bundle result = ModularizationDelegate.getInstance().getData(ModuleConstans.MODULE_USER_CENTER_NAME+":userCenter:isLogined",null,new Object[]{});
            if (result != null && result.containsKey("result")){
                boolean isLogined = result.getBoolean("result");
                if (isLogined){
                    if (mReplyView == null){
                        mReplyView = stub_can_reply.inflate();
                    }
                    stub_can_reply.setVisibility(View.VISIBLE);
                    stub_not_login.setVisibility(View.GONE);

                }else {
                    if (mNotLoginView == null){
                        mNotLoginView = stub_not_login.inflate();
                    }
                    stub_not_login.setVisibility(View.VISIBLE);
                    stub_can_reply.setVisibility(View.GONE);
                    Button login = (Button) mNotLoginView.findViewById(R.id.login);
                    login.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                ModularizationDelegate.getInstance().runStaticAction(ModuleConstans.MODULE_USER_CENTER_NAME+":startLoginActivity",null,null,new Object[]{getActivity(),true});
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

    private void loadTopicDetail(){
        Observable<TopicContent> observable = TopicSDK.newInstance().getTopicsDetail(mTopicBean.id, new DefaultCallback<TopicContent>() {


            @Override
            public void onSuccess(TopicContent topicContent) {
                showWebviewData(topicContent);
            }

            @Override
            public void onFailed(Throwable e) {
                NLog.d(ABaseFragment.TAG, "onFailed e = %s", e);
            }


        });
        //getCompositeSubscription().add(observable);

    }


    private void initMarkdownView() {
        FrameLayout layout = (FrameLayout) findViewById(R.id.webview_container);
        mMarkdownView = new MarkdownView(getContext());
        mMarkdownView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        layout.addView(mMarkdownView);

        WebImageListener listener = new WebImageListener(getContext(), BaseActivity.class);
        mMarkdownView.addJavascriptInterface(listener, "listener");
        mWebViewClient = new GcsMarkdownViewClient(getContext());
        mMarkdownView.setWebViewClient(mWebViewClient);
    }


    private void showWebviewData(TopicContent topic){
        mMarkdownView.setMarkDownText(topic.getBody());
    }

}
