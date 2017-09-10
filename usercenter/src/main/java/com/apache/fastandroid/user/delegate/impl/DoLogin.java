package com.apache.fastandroid.user.delegate.impl;

import android.os.Bundle;
import android.text.TextUtils;

import com.apache.fastandroid.artemis.comBridge.DelegateException;
import com.apache.fastandroid.artemis.comBridge.IActionDelegate;
import com.apache.fastandroid.artemis.rx.RxUtils;
import com.apache.fastandroid.artemis.support.bean.Token;
import com.apache.fastandroid.artemis.support.bean.UserDetail;
import com.apache.fastandroid.user.UserCenterLog;
import com.apache.fastandroid.user.sdk.UserSDK;
import com.apache.fastandroid.user.support.UserConfigManager;
import com.apache.fastandroid.user.support.cache.UserCache;
import com.tesla.framework.common.util.log.NLog;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by 01370340 on 2017/9/3.
 */

public class DoLogin implements IActionDelegate {
    @Override
    public void runAction(Bundle args, final IActionCallback callback, Object... extras) throws DelegateException {

        UserDetail userDetail = UserCache.getMe();
        String pwd = UserConfigManager.getInstance().getPwd();
        NLog.d(UserCenterLog.getLogTag(), "userDetail = %s", userDetail);
        if (userDetail == null || TextUtils.isEmpty(userDetail.getEmail()) || TextUtils.isEmpty(pwd)){
            if (callback != null){
                callback.onActionFailed(-1, "");
                return;
            }
        }
        Observable<Token> observable = UserSDK.newInstance().login(userDetail.getEmail(),pwd);
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
