package com.apache.fastandroid.user.delegate;

import android.app.Activity;
import android.text.TextUtils;

import com.apache.fastandroid.artemis.ArtemisContext;
import com.apache.fastandroid.artemis.CacheUtil;
import com.apache.fastandroid.artemis.rx.HttpResultObserver;
import com.apache.fastandroid.artemis.support.bean.Token;
import com.apache.fastandroid.artemis.support.bean.UserDetail;
import com.apache.fastandroid.user.sdk.UserSDK;
import com.apache.fastandroid.user.support.UserConfigManager;
import com.apache.fastandroid.user.support.cache.UserCache;
import com.apache.fastandroid.user.support.util.UserCenterLogUtil;
import com.tesla.framework.FrameworkApplication;
import com.tesla.framework.common.util.view.ViewUtils;
import com.tesla.framework.component.bridge.IActionDelegate;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by 01370340 on 2017/9/10.
 */

public class LoginTask {

    public void doAutoLogin(Activity activity,final IActionDelegate.IActionCallback callback){
        UserDetail userDetail = UserCache.getMe();
        String pwd = UserConfigManager.getInstance(FrameworkApplication.getContext()).getPwd();
        UserCenterLogUtil.d("userDetail = %s", userDetail);
        if (userDetail == null || TextUtils.isEmpty(userDetail.getEmail()) || TextUtils.isEmpty(pwd)){
            if (callback != null){
                callback.onActionFailed(-1, "");
                return;
            }
        }
        doLogin(activity,userDetail.getEmail(),pwd,callback);



    }


    public void doLogin(final Activity activity, final String user_name, String password, final IActionDelegate.IActionCallback callback){
        Observable<Token> loginObservable = UserSDK.newInstance().login(user_name,password);
        final Token[] mToken = new Token[1];

        loginObservable
                .flatMap(new Func1<Token, Observable<UserDetail>>() {
                    @Override
                    public Observable<UserDetail> call(Token token) {
                        mToken[0] = token;
                        CacheUtil.saveToken(token);
                        UserCenterLogUtil.d("call token thread = %s", Thread.currentThread().getName());
                        return UserSDK.newInstance().getUserInfo();
                    }
                })
        .subscribeOn(Schedulers.io())// 指定subscribe()发生在IO线程
        .doOnSubscribe(new Action0() {
            @Override
            public void call() {
                UserCenterLogUtil.d("call doOnSubscribe thread = %s",Thread.currentThread().getName());
                ViewUtils.createProgressDialog(activity,"正在登录中....").show();
            }
        })
        .subscribeOn(AndroidSchedulers.mainThread()) //指定subscribe()发生在主线程
        .observeOn(AndroidSchedulers.mainThread()) //指定Subscriber的回调发生在主线程
        .subscribe(new HttpResultObserver<UserDetail>() {
            @Override
            public void onSuccess(UserDetail userDetail) {
                UserCenterLogUtil.d("onNext thread = %s",Thread.currentThread().getName());

                UserCache.saveMe(userDetail);
                ArtemisContext.setUserBean(userDetail);

                if (mToken[0] != null){
                    callback.onActionSuccess(mToken[0]);
                }else {
                    callback.onActionFailed(-100, "getUserInfo onNext loginToken == null");
                }
            }

            @Override
            public void onFailed(Throwable e) {
                UserCenterLogUtil.d("onError thread = %s",Thread.currentThread().getName(), e);
            }

            @Override
            public void onFinished() {
                UserCenterLogUtil.d("onCompleted thread = %s",Thread.currentThread().getName());

                ViewUtils.dismissProgressDialog();
            }
        });

    }
}