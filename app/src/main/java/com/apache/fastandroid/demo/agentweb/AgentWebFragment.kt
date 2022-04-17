package com.apache.fastandroid.demo.agentweb

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.JsResult
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.widget.LinearLayout
import android.widget.TextView
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentAgentWebInActivityBinding
import com.just.agentweb.*
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.ui.fragment.BaseVBFragment

/**
 * Created by Jerry on 2021/9/23.
 */
open class AgentWebFragment:BaseVBFragment<FragmentAgentWebInActivityBinding>(FragmentAgentWebInActivityBinding::inflate) {

    companion object{
        private const val TAG = "AgentWebFragment"
        const val URL_KEY = "url_key"

    }

    protected lateinit var mAgentWeb: AgentWeb

    private lateinit var titleText:TextView


    protected val mWebChromeClient: WebChromeClient = object : WebChromeClient() {
        override fun onReceivedTitle(view: WebView, title: String) {
            super.onReceivedTitle(view, title)
            NLog.d(TAG, "onReceivedTitle title: %s", title)
            titleText.text = title
        }

        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            NLog.d(TAG, "onProgressChanged newProgress: %s,view: %s", newProgress,view)
        }

        override fun onJsAlert(
            view: WebView?,
            url: String?,
            message: String?,
            result: JsResult?
        ): Boolean {
            NLog.d(TAG, "onJsAlert url: %s, message: %s",url,message)
            return super.onJsAlert(view, url, message, result)
        }
    }
    private val mWebViewClient: WebViewClient = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            NLog.d(TAG, "shouldOverrideUrlLoading request: %s", request)
            return super.shouldOverrideUrlLoading(view, request)
        }

        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
            NLog.d(TAG, "onPageStarted url: %s", url)
        }
    }

    private var mPermissionInterceptor = PermissionInterceptor { url, permissions, action ->

        /**
         * PermissionInterceptor 能达到 url1 允许授权， url2 拒绝授权的效果。
         * @param url
         * @param permissions
         * @param action
         * @return true 该Url对应页面请求权限进行拦截 ，false 表示不拦截。
         */
        /**
         * PermissionInterceptor 能达到 url1 允许授权， url2 拒绝授权的效果。
         * @param url
         * @param permissions
         * @param action
         * @return true 该Url对应页面请求权限进行拦截 ，false 表示不拦截。
         */
        /**
         * PermissionInterceptor 能达到 url1 允许授权， url2 拒绝授权的效果。
         * @param url
         * @param permissions
         * @param action
         * @return true 该Url对应页面请求权限进行拦截 ，false 表示不拦截。
         */

        /**
         * PermissionInterceptor 能达到 url1 允许授权， url2 拒绝授权的效果。
         * @param url
         * @param permissions
         * @param action
         * @return true 该Url对应页面请求权限进行拦截 ，false 表示不拦截。
         */
        NLog.d( TAG, "mUrl:" + url + "  permission:" + permissions+ " action:" + action)
        false
    }

    override fun bindUI(rootView: View?) {
        super.bindUI(rootView)
        titleText = findViewById(R.id.detail_title)
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)
        initAgentWeb()
    }

    open fun initAgentWeb(){
        val rootView:LinearLayout = findViewById(R.id.article_detail);
        mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(
                rootView,
                LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
            .useDefaultIndicator(-1, 3)//设置进度条颜色与高度，-1为默认值，高度为2，单位为dp。
            .setWebViewClient(mWebViewClient)
            .setWebChromeClient(mWebChromeClient)
            .setPermissionInterceptor(mPermissionInterceptor)
            .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他应用时，弹窗咨询用户是否前往其他应用
            .interceptUnkownUrl() //拦截找不到相关页面的Scheme
            .createAgentWeb()
            .ready()
            .go(getUrl())
    }


    /**
     * 页面空白，请检查scheme是否加上， scheme://host:port/path?query&query 。
     *
     * @return mUrl
     */
    open fun getUrl(): String {
        return "https://m.jd.com/"
    }
}