package com.apache.fastandroid.artemis.mvp;

import android.content.Context;

import com.apache.fastandroid.artemis.base.BaseFragment;
import com.tesla.framework.network.task.TaskException;

/**
 * Created by jerryliu on 2017/7/9.
 * Fragment的mvp封装
 */

public abstract class BaseMvpFragment<P extends BaseContract.BasePresenter> extends BaseFragment implements BaseContract.BaseView {
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
        mPresenter.detachView();
    }

    public P getPresenter(){
        return mPresenter;
    }


    public abstract P createPresenter();

    @Override
    public void onPrepare() {

    }

    @Override
    public void onFailed(Throwable e) {
        TaskException taskException = new TaskException();
        taskException.setMessage(e.getMessage());
        onTaskStateChanged(ABaseTaskState.falid, taskException);
    }




    @Override
    public void onFinished() {
        onTaskStateChanged(ABaseTaskState.finished, null);
    }


}
