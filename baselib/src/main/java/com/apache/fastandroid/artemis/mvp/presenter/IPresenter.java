package com.apache.fastandroid.artemis.mvp.presenter;

import com.apache.fastandroid.artemis.mvp.view.IView;

/**
 * Created by 01370340 on 2018/4/8.
 */

public interface IPresenter<V extends IView> {

    void attachView(V view);

    void detachView();

    void checkAttachView();

    V getView();

    void cancelRequestTags();


}
