package com.apache.fastandroid.article

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.widget.LinearLayout
import com.apache.fastandroid.databinding.ActivityArticleDetailBinding
import com.just.agentweb.AgentWeb
import com.just.agentweb.WebChromeClient
import com.just.agentweb.WebViewClient
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.ui.activity.BaseVmActivity

/**
 * Created by Jerry on 2021/9/23.
 */
class ArticleDetailActivity : BaseVmActivity<ActivityArticleDetailBinding>(ActivityArticleDetailBinding::inflate) {
    private var articleDetailViewModel: ArticleDetailViewModel? = null
    private val mWebChromeClient: WebChromeClient = object : WebChromeClient() {
        override fun onReceivedTitle(view: WebView, title: String) {
            super.onReceivedTitle(view, title)
            NLog.d(TAG, "onReceivedTitle title: %s", title)
            //            articleDetailViewModel.title.set(title);
            mBinding!!.customBar.detailTitle.text = title
        }
    }
    private val mWebViewClient: WebViewClient = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            NLog.d(TAG, "shouldOverrideUrlLoading request: %s", request)
            return super.shouldOverrideUrlLoading(view, request)
        }

        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
            NLog.d(TAG, "onPageStarted url: %s", url)
        }
    }




    override fun layoutInit(savedInstanceState: Bundle?) {
        super.layoutInit(savedInstanceState)
        val title = intent.getStringExtra("title")
        articleDetailViewModel!!.title.set(intent.getStringExtra("title"))
        articleDetailViewModel!!.url.set(intent.getStringExtra("url"))
        mBinding!!.customBar.detailTitle.text = title
        AgentWeb.with(this)
            .setAgentWebParent(
                mBinding!!.articleDetail,
                LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
            .useDefaultIndicator()
            .setWebChromeClient(mWebChromeClient)
            .createAgentWeb()
            .ready()
            .go(articleDetailViewModel!!.url.get())
        mBinding!!.customBar.detailBack.setOnClickListener { finish() }
    }

    override fun initViewModel() {
        super.initViewModel()
        articleDetailViewModel = getActivityViewModel(ArticleDetailViewModel::class.java)
    }

    companion object {
        const val TAG = "ArticleDetailActivity"

        //
        //    private TextView mTitle;
        //    private View mBack;
        @JvmStatic
        fun launch(from: Activity, title: String?, url: String?) {
            val intent = Intent(from, ArticleDetailActivity::class.java)
            intent.putExtra("title", title)
            intent.putExtra("url", url)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            from.startActivity(intent)
        }
    }
}