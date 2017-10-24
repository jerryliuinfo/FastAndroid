package com.apache.fastandroid.artemis.mvp.rx;

import com.apache.fastandroid.artemis.mvp.BaseContract;
import com.apache.fastandroid.artemis.mvp.MvPresenter;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by 01370340 on 2017/10/10.
 * 基于Rx的Presenter封装,控制订阅的生命周期
 */

public class RxPresenter<T extends BaseContract.BaseView> extends MvPresenter {

    protected CompositeSubscription mCompositeSubscription;

    @Override
    public void detachView() {
        super.detachView();
        unSubscribe();
    }

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


}
