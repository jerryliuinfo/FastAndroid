package com.apache.fastandroid.user.sdk;

import com.apache.fastandroid.artemis.api.TokenService;
import com.apache.fastandroid.artemis.retrofit.BaseHttpUtilsV2;
import com.apache.fastandroid.artemis.support.bean.OAuth;
import com.apache.fastandroid.artemis.support.bean.Token;
import com.apache.fastandroid.artemis.support.bean.UserDetail;
import com.apache.fastandroid.user.support.UserConstans;
import com.tesla.framework.applike.FrameworkApplication;
import com.tesla.framework.network.biz.ABizLogic;
import com.tesla.framework.network.http.HttpConfig;

import rx.Observable;

/**
 * Created by 01370340 on 2017/9/1.
 */

public class UserSDK extends ABizLogic {

    public static UserSDK newInstance(){
        return new UserSDK();
    }

    @Override
    protected HttpConfig configHttpConfig() {
        return new HttpConfig();
    }


    public Observable<Token> login(String username, String password) {
        BaseHttpUtilsV2 httpUtils = BaseHttpUtilsV2.getInstance(FrameworkApplication.getContext(), UserConstans.BASE_URL);
        TokenService apiService = httpUtils.getRetrofit().create(TokenService.class);
        Observable<Token> observable =  apiService.getTokenV2(OAuth.client_id, OAuth.client_secret, OAuth.GRANT_TYPE_LOGIN, username, password);
        return observable;
    }

    public Observable<UserDetail> getUserInfo(){
        BaseHttpUtilsV2 httpUtils = BaseHttpUtilsV2.getInstance(FrameworkApplication.getContext(), UserConstans.BASE_URL);
        UserApi apiService = httpUtils.getRetrofit().create(UserApi.class);
        Observable<UserDetail> observable =  apiService.getUserInfo();
        return observable;
    }






}
