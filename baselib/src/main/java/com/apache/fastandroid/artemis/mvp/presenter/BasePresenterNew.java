package com.apache.fastandroid.artemis.mvp.presenter;

import com.apache.fastandroid.artemis.mvp.view.IView;

/**
 * Created by 01370340 on 2018/4/8.
 */

public class BasePresenterNew<V extends IView> implements IPresenter<V> {
    private V view;
    @Override
    public void attachView(V view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void checkAttachView() {
        if (view == null){
            throw new RuntimeException("You have no binding this view");
        }
    }

    @Override
    public V getView() {
        return view;
    }

    @Override
    public void cancelRequestTags() {

    }




}
