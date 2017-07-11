package com.apache.fastandroid.base.mvp;

import android.content.Context;

import com.apache.fastandroid.base.mvp.base.MvpPresenter;
import com.apache.fastandroid.base.mvp.base.MvpView;
import com.tesla.framework.ui.fragment.ABaseFragment;

/**
 * Created by jerryliu on 2017/7/9.
 */

public abstract class BaseMvpFragment<P extends MvpPresenter> extends ABaseFragment implements MvpView {
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
