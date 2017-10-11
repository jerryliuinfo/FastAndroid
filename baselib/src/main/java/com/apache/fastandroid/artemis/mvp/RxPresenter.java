package com.apache.fastandroid.artemis.mvp;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by 01370340 on 2017/10/10.
 * 基于Rx的Presenter封装,控制订阅的生命周期
 */

public class RxPresenter<V extends MvpView> extends BaseMvpPresenter {

    protected CompositeSubscription mCompositeSubscription;

    protected void unSubscribe(){
        if (mCompositeSubscription != null){
            mCompositeSubscription.unsubscribe();
        }
    }

    protected void addSubscribe(Subscription subscription){
        if (mCompositeSubscription == null){
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        unSubscribe();
    }
}
