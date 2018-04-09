package com.apache.fastandroid.artemis.mvp.view;

/**
 * Created by 01370340 on 2018/4/8.
 */

public interface IView {

    void showLoading(String msg);

    void hideLoading();


    void showFail();

    void showError();

    void showEmpty();
}
