package com.tesla.framework.ui.fragment;

import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.tesla.framework.R;


/**
 * Created by 01370340 on 2017/9/1.
 */

public abstract class BaseStatusFragmentNew extends BaseFragment {

    /**
     * Activity中由于网络异常导致加载失败显示的布局。
     */
    private View badNetworkView;

    private View loadErrorView;

    private View noContentView;

    private View loadingView;



    protected void showLoadingView() {
        loadingView.setVisibility(View.VISIBLE);
    }

    /**
     * 当Activity中的加载内容服务器返回失败，通过此方法显示提示界面给用户。
     *
     * 界面中的提示信息
     */
    protected void showLoadErrorView(String text,View.OnClickListener onClickListener) {
        if (loadErrorView != null) {
            loadErrorView.setVisibility(View.VISIBLE);
            return;
        }
        ViewStub viewStub = findViewById(R.id.layoutLoadFailed);
        if (viewStub != null) {
            loadErrorView = viewStub.inflate();
            TextView loadErrorText = loadErrorView.findViewById(R.id.txtLoadFailed);
            if (!StringUtils.isEmpty(text)){
                loadErrorText.setText(text);
            }

            View tv_retry = loadErrorView.findViewById(R.id.layoutReload);
            tv_retry.setOnClickListener(onClickListener);
        }
    }


    /**
     * 当Activity中的内容因为网络原因无法显示的时候，通过此方法显示提示界面给用户。
     *
     * 重新加载点击事件回调
     */
    protected void showBadNetworkView(View.OnClickListener exitListener) {
        if (badNetworkView != null) {
            badNetworkView.setVisibility(View.VISIBLE);
            return;
        }
        ViewStub viewStub = findViewById(R.id.layoutBadNetwork);
        if (viewStub != null) {
            badNetworkView = viewStub.inflate();
            View goSetting = badNetworkView.findViewById(R.id.btnGoSetting);
            goSetting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }
    }


    protected void showNoContentView(String tip) {
        if (noContentView != null) {
            noContentView.setVisibility(View.VISIBLE);
            return;
        }
        ViewStub viewStub = findViewById(R.id.layoutEmpty);
        if (viewStub != null) {
            noContentView = viewStub.inflate();
            TextView noContentText = noContentView.findViewById(R.id.txtLoadEmpty);
            if (!StringUtils.isEmpty(tip)){
                noContentText.setText(tip);
            }
        }
    }



    /**
     * 将load error view进行隐藏。
     */
    protected void hideLoadErrorView() {
        if (loadErrorView != null && loadErrorView.getVisibility() == View.VISIBLE){
            loadErrorView.setVisibility(View.GONE);
        }
    }


    protected void hideEmptyView() {
        if (noContentView != null && noContentView.getVisibility() == View.VISIBLE){
            noContentView.setVisibility(View.GONE);
        }
    }

    protected void hidLoadingView() {
        if (loadingView != null && loadingView.getVisibility() == View.VISIBLE){
            loadingView.setVisibility(View.GONE);
        }
    }
}
