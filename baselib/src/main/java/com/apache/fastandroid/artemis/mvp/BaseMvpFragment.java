package com.apache.fastandroid.artemis.mvp;

import android.content.Context;

import com.apache.fastandroid.artemis.base.BaseFragment;

/**
 * Created by jerryliu on 2017/7/9.
 */

public abstract class BaseMvpFragment<P extends MvpPresenter> extends BaseFragment implements MvpView {
    private P mPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mPresenter = createPresenter();
        mPresenter.attachView(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPresenter.detachView(false);
    }

    protected P getPresenter(){
        return mPresenter;
    }


    public abstract P createPresenter();

}
