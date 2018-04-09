package com.apache.fastandroid.artemis.mvp;

import android.os.Bundle;

import com.apache.fastandroid.artemis.mvp.presenter.BasePresenterNew;
import com.apache.fastandroid.artemis.mvp.view.IView;
import com.tesla.framework.ui.activity.BaseActivity;

/**
 * Created by jerryliu on 2017/7/9.
 * Activity的MVP封装
 */

public abstract class BaseMVPActivity<P extends BasePresenterNew<V>, V extends IView> extends BaseActivity implements IView {
    protected P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        attachView();
    }

    private void attachView(){
        if (getPresenter() != null){
            getPresenter().attachView((V) this);
        }
    }

    private void detachView(){
        if (getPresenter() != null){
            getPresenter().detachView();
        }
    }

    private void cancelRequestTags(){
        if (getPresenter() != null){
            getPresenter().cancelRequestTags();
        }
    }


    public P getPresenter(){
        return mPresenter;
    }

    @Override
    public void onDestroy() {
        detachView();
        cancelRequestTags();
        super.onDestroy();
    }

    public abstract P createPresenter();
}
