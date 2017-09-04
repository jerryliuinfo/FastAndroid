package com.apache.fastandroid.user.sdk;

import com.apache.fastandroid.artemis.BaseBizLogic;
import com.apache.fastandroid.artemis.CacheUtil;
import com.apache.fastandroid.artemis.api.TokenService;
import com.apache.fastandroid.artemis.comBridge.IActionDelegate;
import com.apache.fastandroid.artemis.retrofit.BaseHttpUtilsV2;
import com.apache.fastandroid.artemis.support.bean.OAuth;
import com.apache.fastandroid.artemis.support.bean.Token;
import com.apache.fastandroid.artemis.support.bean.UserDetail;
import com.apache.fastandroid.user.support.cache.UserCache;
import com.tesla.framework.FrameworkApplication;
import com.tesla.framework.network.http.HttpConfig;
import com.tesla.framework.network.task.TaskException;

import retrofit2.Call;
import retrofit2.Response;
import rx.Observable;

/**
 * Created by 01370340 on 2017/9/1.
 */

public class UserSDK extends BaseBizLogic {

    public static UserSDK newInstance(){
        return new UserSDK();
    }

    @Override
    protected HttpConfig configHttpConfig() {
        return new HttpConfig();
    }

    /**
     * 返回token信息
     * @return
     */
    public Token login(String user_name, String password) throws Exception{
        BaseHttpUtilsV2 httpUtils = BaseHttpUtilsV2.getInstance(FrameworkApplication.getContext(), "https://diycode.cc/api/v3/");
        TokenService apiService = httpUtils.getRetrofit().create(TokenService.class);
        Call<Token> call =  apiService.getToken(OAuth.client_id, OAuth.client_secret, OAuth.GRANT_TYPE_LOGIN, user_name, password);
        if (call != null){
            Response<Token> response =  call.execute();
            if (response != null && response.isSuccessful()){
                Token token =  response.body();
                return token;
            }
        }
        return null;
    }
    public Observable<Token> loginV2(String user_name, String password) {
        BaseHttpUtilsV2 httpUtils = BaseHttpUtilsV2.getInstance(FrameworkApplication.getContext(), "https://diycode.cc/api/v3/");
        TokenService apiService = httpUtils.getRetrofit().create(TokenService.class);
        Observable<Token> observable =  apiService.getTokenV2(OAuth.client_id, OAuth.client_secret, OAuth.GRANT_TYPE_LOGIN, user_name, password);
        return observable;
    }


    /**
     * 执行自动登录
     * @param callback
     * @throws TaskException
     */
    public void doAutoLogin(IActionDelegate.IActionCallback callback) throws TaskException{
        if (!CacheUtil.isLogin()){
            callback.onActionFailed(-1, "");
            callback.onActionFinish();
            return ;
        }else {
            //UserBean userBean = UserConfigManager.getInstance().getUserBean();
            UserDetail userBean = UserCache.getMe();
            boolean result = false;
            Token loginResult = null;
            try {
                loginResult =  login(userBean.getLogin(),"gree3502");
                if (loginResult != null){
                    result = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (result){
                callback.onActionSuccess(loginResult);
            }else {
                callback.onActionFailed(-1, "自动登录失败");
            }
            callback.onActionFinish();
        }
    }


    public Observable<UserDetail> getMe(){
        BaseHttpUtilsV2 httpUtils = BaseHttpUtilsV2.getInstance(FrameworkApplication.getContext(), "https://diycode.cc/api/v3/");
        UserApi apiService = httpUtils.getRetrofit().create(UserApi.class);
        Observable<UserDetail> observable =  apiService.getMe();
        return observable;
    }




}
