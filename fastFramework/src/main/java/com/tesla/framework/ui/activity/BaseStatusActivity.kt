package com.tesla.framework.ui.activity

import android.app.ProgressDialog
import android.view.View
import android.view.ViewStub
import android.widget.TextView
import com.tesla.framework.R

/**
 * Created by Jerry on 2021/3/26.
 * 封装
 */
abstract class BaseStatusActivity:BaseActivity() {
    /**
     * Activity中由于服务器异常导致加载失败显示的布局。
     */
    private var loadErrorView: View? = null

    /**
     * Activity中由于网络异常导致加载失败显示的布局。
     */
    private var badNetworkView: View? = null

    /**
     * Activity中当界面上没有任何内容时展示的布局。
     */
    private var noContentView: View? = null

    private var progressDialog: ProgressDialog? = null


    /**
     * 当Activity中的加载内容服务器返回失败，通过此方法显示提示界面给用户。
     *
     * @param tip
     * 界面中的提示信息
     */
    protected fun showLoadErrorView(tip: String) {
        if (loadErrorView != null) {
            loadErrorView?.visibility = View.VISIBLE
            return
        }
        val viewStub = findViewById<ViewStub>(R.id.layoutLoadFailed)
        if (viewStub != null) {
            loadErrorView = viewStub.inflate()
            val loadErrorText = loadErrorView?.findViewById<TextView>(R.id.txtLoadFailed)
            loadErrorText?.text = tip
        }
    }

    /**
     * 当Activity中的内容因为网络原因无法显示的时候，通过此方法显示提示界面给用户。
     *
     * @param listener
     * 重新加载点击事件回调
     */
    protected fun showBadNetworkView(listener: View.OnClickListener) {
        if (badNetworkView != null) {
            badNetworkView?.visibility = View.VISIBLE
            return
        }
        val viewStub = findViewById<ViewStub>(R.id.badNetworkView)
        if (viewStub != null) {
            badNetworkView = viewStub.inflate()
            val badNetworkRootView = badNetworkView?.findViewById<View>(R.id.badNetworkRootView)
            badNetworkRootView?.setOnClickListener(listener)
        }
    }

    /**
     * 当Activity中没有任何内容的时候，通过此方法显示提示界面给用户。
     * @param tip
     * 界面中的提示信息
     */
    protected fun showNoContentView(tip: String) {
        if (noContentView != null) {
            noContentView?.visibility = View.VISIBLE
            return
        }
        val viewStub = findViewById<ViewStub>(R.id.layoutEmpty)
        if (viewStub != null) {
            noContentView = viewStub.inflate()
            val noContentText = noContentView?.findViewById<TextView>(R.id.txtLoadEmpty)
            noContentText?.text = tip
        }
    }

    /**
     * 将load error view进行隐藏。
     */
    protected fun hideLoadErrorView() {
        loadErrorView?.visibility = View.GONE
    }

    /**
     * 将no content view进行隐藏。
     */
    protected fun hideNoContentView() {
        noContentView?.visibility = View.GONE
    }

    /**
     * 将bad network view进行隐藏。
     */
    protected fun hideBadNetworkView() {
        badNetworkView?.visibility = View.GONE
    }

    fun showProgressDialog(title: String?, message: String) {
        if (progressDialog == null) {
            progressDialog = ProgressDialog(this).apply {
                if (title != null) {
                    setTitle(title)
                }
                setMessage(message)
                setCancelable(false)
            }
        }
        progressDialog?.show()
    }

    fun closeProgressDialog() {
        progressDialog?.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }
    }
}