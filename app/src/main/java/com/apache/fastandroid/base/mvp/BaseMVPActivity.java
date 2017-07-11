package com.apache.fastandroid.base.mvp;

import android.os.Bundle;

import com.apache.fastandroid.base.mvp.base.MvpPresenter;
import com.apache.fastandroid.base.mvp.base.MvpView;
import com.tesla.framework.ui.activity.BaseActivity;

/**
 * Created by jerryliu on 2017/7/9.
 */

public abstract class BaseMVPActivity<P extends MvpPresenter> extends BaseActivity implements MvpView {
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
        mPresenter.detachView(false);
    }

    public abstract P createPresenter();
}
