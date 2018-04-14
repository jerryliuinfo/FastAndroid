package com.apache.fastandroid.artemis.mvp.view;

/**
 * Created by 01370340 on 2018/4/8.
 */

public interface IView {

    void showLoading(String msg);

    void hideLoading();

    /**
     * 显示错误
     */
    void showFail(String errorMsg);

    /**
     * 显示错误
     */
    void showError();

    /**
     * 显示没有数据
     */
    void showEmpty();
}
