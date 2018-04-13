package com.apache.fastandroid.artemis.mvp.rx;

import com.apache.fastandroid.artemis.mvp.presenter.BasePresenterNew;
import com.apache.fastandroid.artemis.mvp.view.IView;


/**
 * Created by 01370340 on 2017/10/10.
 * 基于Rx的Presenter封装,控制订阅的生命周期
 */

public class RxPresenterNew<V extends IView> extends BasePresenterNew<V> {

//    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();
//
//    protected void unSubscribe(){
//        if (mCompositeSubscription != null){
//            mCompositeSubscription.unsubscribe();
//            mCompositeSubscription = null;
//        }
//    }
//
//    protected void addSubscribe(Subscription subscription){
//        mCompositeSubscription.add(subscription);
//    }



}
