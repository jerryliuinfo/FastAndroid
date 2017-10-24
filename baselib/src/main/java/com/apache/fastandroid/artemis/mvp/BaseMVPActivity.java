package com.apache.fastandroid.artemis.mvp;

import android.os.Bundle;

import com.tesla.framework.ui.activity.BaseActivity;

/**
 * Created by jerryliu on 2017/7/9.
 */

public abstract class BaseMVPActivity<P extends MvPresenter> extends BaseActivity implements BaseContract.BaseView {
    private P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        mPresenter.attachView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    public abstract P createPresenter();
}
