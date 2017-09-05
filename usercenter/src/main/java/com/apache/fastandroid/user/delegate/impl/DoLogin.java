package com.apache.fastandroid.user.delegate.impl;

import android.os.Bundle;

import com.apache.fastandroid.artemis.comBridge.DelegateException;
import com.apache.fastandroid.artemis.comBridge.IActionDelegate;
import com.apache.fastandroid.artemis.rx.RxUtils;
import com.apache.fastandroid.artemis.support.bean.Token;
import com.apache.fastandroid.user.sdk.UserSDK;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by 01370340 on 2017/9/3.
 */

public class DoLogin implements IActionDelegate {
    @Override
    public void runAction(Bundle args, final IActionCallback callback, Object... extras) throws DelegateException {
        Observable<Token> observable = UserSDK.newInstance().login("liuxiangxiang1234@163.com","gree3502");
        Subscriber subscriber = new Subscriber<Token>() {
            @Override
            public void onCompleted() {
                if (callback != null){
                    callback.onActionFinish();
                }
            }

            @Override
            public void onError(Throwable e) {
                if (callback != null){
                    callback.onActionFailed(-1, e.getMessage());
                }
            }

            @Override
            public void onNext(Token token) {
                if (token != null){
                    if (callback != null){
                        callback.onActionSuccess(token);
                    }
                }else {
                    callback.onActionFailed(-1, "loginError");
                }
            }


        };
         observable.compose(RxUtils.defaultSchedulers()).subscribe(subscriber);
    }
}
