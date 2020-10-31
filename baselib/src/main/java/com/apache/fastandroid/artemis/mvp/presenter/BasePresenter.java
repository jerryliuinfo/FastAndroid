package com.apache.fastandroid.artemis.mvp.presenter;

import androidx.lifecycle.LifecycleRegistry;

import com.apache.fastandroid.artemis.mvp.view.IView;

import java.lang.ref.WeakReference;

/**
 * Created by 01370340 on 2018/4/8.
 */

public class BasePresenter<V extends IView> implements IPresenter<V> {
    private WeakReference<V> mViewRef;
    @Override
    public void attachView(V view) {
        this.mViewRef = new WeakReference<>(view);
    }

    @Override
    public void detachView() {
        this.mViewRef = null;
    }

    @Override
    public void checkAttachView() {
        if (getViewRef() == null){
            throw new RuntimeException("You have no binding this mViewRef");
        }
    }

    public V getViewRef() {
        if (mViewRef != null){
            return mViewRef.get();
        }
        return null;
    }

    @Override
    public void cancelRequestTags() {
        LifecycleRegistry lifecycleRegistry;

    }



}
