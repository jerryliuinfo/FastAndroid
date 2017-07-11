package com.apache.fastandroid.base.mvp.base;

/**
 * Created by jerryliu on 2017/7/9.
 */

public interface MvpPresenter<V extends MvpView> {
    /**
     * 视图绑定
     */
    void attachView(V view);

    /**
     * 分离视图。
     *
     * @param retainInstance 区分是 Activity.detachView()  Or  Fragment.onDestroyView();
     */
    void detachView(boolean retainInstance);
}
