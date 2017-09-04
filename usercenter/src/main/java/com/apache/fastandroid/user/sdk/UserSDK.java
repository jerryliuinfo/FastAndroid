package com.apache.fastandroid.user.sdk;

import com.apache.fastandroid.artemis.BaseBizLogic;
import com.apache.fastandroid.artemis.comBridge.IActionDelegate;
import com.apache.fastandroid.artemis.http.BaseHttpUtilsV2;
import com.apache.fastandroid.artemis.http.TokenService;
import com.apache.fastandroid.artemis.support.bean.OAuth;
import com.apache.fastandroid.artemis.support.bean.Token;
import com.apache.fastandroid.artemis.support.bean.UserBean;
import com.apache.fastandroid.user.UserConfigManager;
import com.tesla.framework.FrameworkApplication;
import com.tesla.framework.network.http.HttpConfig;
import com.tesla.framework.network.task.TaskException;

import retrofit2.Call;
import retrofit2.Response;

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


    /**
     * 执行自动登录
     * @param callback
     * @throws TaskException
     */
    public void doAutoLogin(IActionDelegate.IActionCallback callback) throws TaskException{
        if (!UserConfigManager.getInstance().isLastTimeLogined()){
            callback.onActionFailed(-1, "");
            callback.onActionFinish();
            return ;
        }else {
            UserBean userBean = UserConfigManager.getInstance().getUserBean();
            boolean result = false;
            Token loginResult = null;
            try {
                loginResult =  login(userBean.getUserName(),userBean.getPassword());
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




}
