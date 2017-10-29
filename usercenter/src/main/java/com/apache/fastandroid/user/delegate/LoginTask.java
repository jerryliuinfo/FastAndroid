package com.apache.fastandroid.user.delegate;

import android.text.TextUtils;

import com.apache.fastandroid.artemis.CacheUtil;
import com.apache.fastandroid.artemis.comBridge.IActionDelegate;
import com.apache.fastandroid.artemis.support.bean.Token;
import com.apache.fastandroid.artemis.support.bean.UserDetail;
import com.apache.fastandroid.user.UserCenterLog;
import com.apache.fastandroid.user.sdk.UserSDK;
import com.apache.fastandroid.user.support.UserConfigManager;
import com.apache.fastandroid.user.support.cache.UserCache;
import com.tesla.framework.FrameworkApplication;
import com.tesla.framework.common.util.log.NLog;
import com.tesla.framework.network.task.TaskException;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by 01370340 on 2017/9/10.
 */

public class LoginTask {

    public void doAutoLogin(final IActionDelegate.IActionCallback callback){





        UserDetail userDetail = UserCache.getMe();
        String pwd = UserConfigManager.getInstance(FrameworkApplication.getContext()).getPwd();
        NLog.d(UserCenterLog.getLogTag(), "userDetail = %s", userDetail);
        if (userDetail == null || TextUtils.isEmpty(userDetail.getEmail()) || TextUtils.isEmpty(pwd)){
            if (callback != null){
                callback.onActionFailed(-1, "");
                return;
            }
        }
        doLogin(userDetail.getEmail(),pwd,callback);



    }


    public void doLogin(String user_name, String password, final IActionDelegate.IActionCallback callback){
        final Token[] loginToken = {null};
        Observable<Token> loginObservable = UserSDK.newInstance().login(user_name,password);

        loginObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Action1<Token>() {
                    @Override
                    public void call(Token token) {
                        NLog.d(UserCenterLog.getLogTag(), "subscribeOn call token = %s", token);
                        loginToken[0] = token;
                        CacheUtil.saveToken(token);
                    }
                }).observeOn(Schedulers.io())
                .flatMap(new Func1<Token, Observable<UserDetail>>() {
                    @Override
                    public Observable<UserDetail> call(Token token) {
                        return UserSDK.newInstance().getMe();
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserDetail>() {
                    @Override
                    public void onCompleted() {
                        NLog.d(UserCenterLog.getLogTag(), "onCompleted");

                    }

                    @Override
                    public void onError(Throwable e) {
                        NLog.d(UserCenterLog.getLogTag(), "onError");
                        if (loginToken[0] != null){
                            callback.onActionSuccess(loginToken[0]);
                        }else {
                            callback.onActionSuccess(new TaskException(e.getMessage()));
                        }
                    }

                    @Override
                    public void onNext(UserDetail userDetail) {
                        NLog.d(UserCenterLog.getLogTag(), "onNext userDetail = %s",userDetail);
                        UserCache.saveMe(userDetail);
                        if (loginToken[0] != null){
                            callback.onActionSuccess(loginToken[0]);
                        }else {
                            callback.onActionSuccess(new TaskException(""));
                        }
                    }
                });
    }
}