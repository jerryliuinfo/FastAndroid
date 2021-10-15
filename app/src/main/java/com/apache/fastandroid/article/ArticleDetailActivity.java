package com.apache.fastandroid.article;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apache.fastandroid.BR;
import com.apache.fastandroid.R;
import com.apache.fastandroid.databinding.ActivityArticleDetailBinding;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.WebChromeClient;
import com.just.agentweb.WebViewClient;
import com.tesla.framework.common.util.log.NLog;
import com.tesla.framework.support.bean.DataBindingConfig;
import com.tesla.framework.ui.activity.BaseDatabindingActivity;

/**
 * Created by Jerry on 2021/9/23.
 */
public class ArticleDetailActivity extends BaseDatabindingActivity<ActivityArticleDetailBinding> {
    public static final String TAG = "ArticleDetailActivity";

    private ArticleDetailViewModel articleDetailViewModel;

    private TextView mTitle;
    private View mBack;


    public static void launch(Activity from,String title,String url) {
        Intent intent = new Intent(from, ArticleDetailActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("url",url);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        from.startActivity(intent);
    }

    private com.just.agentweb.WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            NLog.d(TAG, "onReceivedTitle title: %s",title);
            articleDetailViewModel.title.set(title);
            mTitle.setText(title);
        }
    };

    private com.just.agentweb.WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            NLog.d(TAG, "shouldOverrideUrlLoading request: %s",request);

            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            NLog.d(TAG, "onPageStarted url: %s",url);
        }
    };

    @Override
    protected void initViewModel() {
        articleDetailViewModel = getActivityViewModel(ArticleDetailViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.activity_article_detail, BR.vm, articleDetailViewModel);

    }


    @Override
    protected void bindView() {
        super.bindView();

        mTitle =  getBinding().getRoot().findViewById(R.id.detail_title);
        mBack =  getBinding().getRoot().findViewById(R.id.detail_back);
    }

    @Override
    public void layoutInit(Bundle savedInstanceState) {
        super.layoutInit(savedInstanceState);

        String title = getIntent().getStringExtra("title");
        articleDetailViewModel.title.set(getIntent().getStringExtra("title"));
        articleDetailViewModel.url.set(getIntent().getStringExtra("url"));
        mTitle.setText(title);


        AgentWeb.with(this)
                .setAgentWebParent(mBinding.articleDetail, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
                .useDefaultIndicator()
                .setWebChromeClient(mWebChromeClient)
                .createAgentWeb()
                .ready()
                .go(articleDetailViewModel.url.get());

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
