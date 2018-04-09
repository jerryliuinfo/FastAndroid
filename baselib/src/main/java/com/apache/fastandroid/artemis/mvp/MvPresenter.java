package com.apache.fastandroid.artemis.mvp;

import java.lang.ref.WeakReference;

/**
 * Created by 01370340 on 2017/10/21.
 */

public class MvPresenter<V extends BaseContract.BaseView> implements BaseContract.BasePresenter<V> {

    protected WeakReference<V> mViewRef;
    @Override
    public void attachView(V view) {
        mViewRef = new WeakReference<>(view);
    }

    @Override
    public void detachView() {
        if (mViewRef != null){
            mViewRef.clear();
            mViewRef = null;
        }
    }

    public V getView(){
        if (mViewRef != null){
            return mViewRef.get();
        }
        return null;
    }
}
