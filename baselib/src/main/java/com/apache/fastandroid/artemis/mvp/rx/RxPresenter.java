package com.apache.fastandroid.artemis.mvp.rx;

import com.apache.fastandroid.artemis.mvp.BaseContract;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by 01370340 on 2017/10/10.
 * 基于Rx的Presenter封装,控制订阅的生命周期
 */

public class RxPresenter<T extends BaseContract.BaseView> implements BaseContract.BasePresenter<T> {

    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();
    private T mView;

    protected void unSubscribe(){
        if (mCompositeSubscription != null){
            mCompositeSubscription.unsubscribe();
            mCompositeSubscription = null;
        }
    }

    protected void addSubscribe(Subscription subscription){
        mCompositeSubscription.add(subscription);
    }


    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
        unSubscribe();
    }

    public T getView(){
        return mView;
    }
}
