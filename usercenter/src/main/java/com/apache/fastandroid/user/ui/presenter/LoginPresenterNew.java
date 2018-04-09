package com.apache.fastandroid.user.ui.presenter;

import android.app.Activity;
import android.text.TextUtils;

import com.apache.fastandroid.artemis.ArtemisContext;
import com.apache.fastandroid.artemis.CacheUtil;
import com.apache.fastandroid.artemis.mvp.presenter.BasePresenterNew;
import com.apache.fastandroid.artemis.rx.HttpResultObserver;
import com.apache.fastandroid.artemis.support.bean.Token;
import com.apache.fastandroid.artemis.support.bean.UserDetail;
import com.apache.fastandroid.user.sdk.UserSDK;
import com.apache.fastandroid.user.support.UserConfigManager;
import com.apache.fastandroid.user.support.cache.UserCache;
import com.apache.fastandroid.user.support.util.UserCenterLogUtil;
import com.apache.fastandroid.user.ui.contract.LoginContractNew;
import com.tesla.framework.applike.FrameworkApplication;
import com.tesla.framework.common.util.view.ViewUtils;
import com.tesla.framework.component.bridge.IActionDelegate;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by 01370340 on 2017/12/7.
 */

public class LoginPresenterNew extends BasePresenterNew<LoginContractNew.View> implements LoginContractNew.Presenter {
    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();


    IActionDelegate.IActionCallback callback;

    public void setCallback(IActionDelegate.IActionCallback callback) {
        this.callback = callback;
    }

    @Override
    public void doAutoLogin(Activity activityk) {
        UserDetail userDetail = UserCache.getMe();
        String pwd = UserConfigManager.getInstance(FrameworkApplication.getContext()).getPwd();
        UserCenterLogUtil.d("userDetail = %s", userDetail);
        if (userDetail == null || TextUtils.isEmpty(userDetail.getEmail()) || TextUtils.isEmpty(pwd)){
            if (callback != null){
                callback.onActionFailed(-1, "");
            }

        }else {
            //直接登录
            doLogin(userDetail.getEmail(),pwd);
        }

    }

    @Override
    public void doLogin(String userName, String pwd) {
        if (callback != null){
            callback.onActionPrepare();
        }
        if (getView() != null){
            getView().showLoading("正在登陆中");
        }

        Observable<Token> loginObservable = UserSDK.newInstance().login(userName,pwd);
        final Token[] mToken = new Token[1];

        Subscription subscription =loginObservable
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
                .observeOn(AndroidSchedulers.mainThread()) //指定Subscriber的回调发生在主线程
                .subscribe(new HttpResultObserver<UserDetail>() {
                    @Override
                    public void onSuccess(UserDetail userDetail) {
                        UserCenterLogUtil.d("onNext thread = %s",Thread.currentThread().getName());

                        UserCache.saveMe(userDetail);
                        ArtemisContext.setUserBean(userDetail);

                        if (mToken[0] != null){
                            if (callback != null){
                                callback.onActionSuccess(userDetail);
                            }
                            if (getView() != null){
                                getView().getUserInfo(userDetail);
                            }

                        }else {
                            if (callback != null){
                                callback.onActionFailed(-100, "getUserInfo onNext loginToken == null");
                            }
                            if (getView() != null){
                                //getView().onFailed(new Exception(""));
                                getView().showError();
                            }

                        }
                    }

                    @Override
                    public void onFailed(Throwable e) {
                        UserCenterLogUtil.d("onError thread = %s",Thread.currentThread().getName(), e);
                        if (getView() != null){
                            //getView().onFailed(e);
                            getView().showFail();
                        }
                        if (callback != null){
                            callback.onActionFailed(-100,"登录失败");
                        }

                    }

                    @Override
                    public void onFinished() {
                        UserCenterLogUtil.d("onCompleted thread = %s",Thread.currentThread().getName());

                        ViewUtils.dismissProgressDialog();
                       /* if (getView() != null){
                            getView().onFinished();
                        }*/
                        if (callback != null){
                            callback.onActionFinish();
                        }

                    }
                });
        addSubscribe(subscription);
    }

    @Override
    public void detachView() {
        super.detachView();
        unSubscribe();
    }

    protected void unSubscribe(){
        if (mCompositeSubscription != null){
            mCompositeSubscription.unsubscribe();
            mCompositeSubscription = null;
        }
    }

    protected void addSubscribe(Subscription subscription){
        mCompositeSubscription.add(subscription);
    }


}
