package com.apache.fastandroid.artemis.mvp;

import java.lang.ref.WeakReference;

/**
 * Created by 01370340 on 2017/10/21.
 */

public class MvPresenter<T extends BaseContract.BaseView> implements BaseContract.BasePresenter<T> {

    protected WeakReference<T> mViewRef;
    @Override
    public void attachView(T view) {
        mViewRef = new WeakReference<>(view);
    }

    @Override
    public void detachView() {
        if (mViewRef != null){
            mViewRef.clear();
            mViewRef = null;
        }
    }

    public T getView(){
        if (mViewRef != null){
            return mViewRef.get();
        }
        return null;
    }
}
