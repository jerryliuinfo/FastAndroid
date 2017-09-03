package com.apache.fastandroid.artemis.mvp;

import java.lang.ref.WeakReference;

/**
 * Created by jerryliu on 2017/7/9.
 */

public  class BaseMvpPresenter<V extends MvpView> implements MvpPresenter {
    public static final String TAG = BaseMvpPresenter.class.getSimpleName();
    protected WeakReference<V> mViewRef;
    protected V mView;
    @Override
    public void attachView(MvpView view) {
        mViewRef = (WeakReference<V>) new WeakReference<>(view);
        mView = mViewRef.get();
    }

    @Override
    public void detachView(boolean retainInstance) {
        if (mViewRef != null){
            mViewRef.clear();
            mView = null;
        }
    }

    public V getView(){
        if (mViewRef != null){
            return mViewRef.get();
        }
        return null;
    }
}
