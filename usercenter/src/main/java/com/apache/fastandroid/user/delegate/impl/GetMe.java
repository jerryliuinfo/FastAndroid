package com.apache.fastandroid.user.delegate.impl;

import android.os.Bundle;

import com.tesla.framework.component.bridge.DelegateException;
import com.tesla.framework.component.bridge.IActionDelegate;
import com.apache.fastandroid.artemis.rx.RxUtils;
import com.apache.fastandroid.artemis.support.bean.UserDetail;
import com.apache.fastandroid.user.sdk.UserSDK;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by 01370340 on 2017/9/3.
 */

public class GetMe implements IActionDelegate {
    @Override
    public void runAction(Bundle args, IActionCallback callback, Object... extras) throws DelegateException {
        Observable<UserDetail> observable = UserSDK.newInstance().getMe();
        Subscriber subscriber = new Subscriber<UserDetail>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(UserDetail userDetail) {

            }
        };
        observable.compose(RxUtils.defaultSchedulers()).subscribe(subscriber);
    }
}
