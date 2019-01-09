package com.apache.fastandroid.artemis.mvp;

import android.content.Context;

import com.apache.fastandroid.artemis.base.BaseFragment;
import com.apache.fastandroid.artemis.mvp.presenter.BasePresenter;
import com.apache.fastandroid.artemis.mvp.view.IView;

/**
 * Created by jerryliu on 2017/7/9.
 * Fragment的mvp封装
 */

public abstract class BaseMvpFragment<P extends BasePresenter<V>, V extends IView> extends BaseFragment implements IView {
    private P mPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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



    @Override
    public void showLoading(String msg) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showFail(String errorMsg) {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showError() {

    }
}

