package com.apache.fastandroid.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.apache.fastandroid.BR;
import com.apache.fastandroid.R;
import com.apache.fastandroid.databinding.ActivityArticleDetailBinding;
import com.apache.fastandroid.home.detail.ArticleDetailViewModel;
import com.just.agentweb.AgentWeb;
import com.tesla.framework.support.bean.DataBindingConfig;
import com.tesla.framework.ui.activity.BaseDatabindingActivity;

/**
 * Created by Jerry on 2021/9/23.
 */
public class ArticleDetailActivity extends BaseDatabindingActivity<ActivityArticleDetailBinding> {

    private ArticleDetailViewModel articleDetailViewModel;


    public static void launch(Activity from,String title,String url) {
        Intent intent = new Intent(from, ArticleDetailActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("url",url);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        from.startActivity(intent);
    }

    @Override
    protected void initViewModel() {
        articleDetailViewModel = getActivityViewModel(ArticleDetailViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.activity_article_detail, BR.vm, articleDetailViewModel)
                .addBindingParam(BR.clickProxy,new ClickProxy());
    }



    @Override
    public void layoutInit(Bundle savedInstanceState) {
        super.layoutInit(savedInstanceState);

        articleDetailViewModel.title.set(getIntent().getStringExtra("title"));
        articleDetailViewModel.url.set(getIntent().getStringExtra("url"));


        AgentWeb mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mBinding.articleDetail, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go(articleDetailViewModel.url.get());
    }

    public class ClickProxy{
        public void onBack(){
            finish();
        }
    }
}
