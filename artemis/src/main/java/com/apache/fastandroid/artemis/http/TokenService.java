package com.apache.fastandroid.artemis.http;

import com.apache.fastandroid.artemis.Constants;
import com.apache.fastandroid.artemis.support.bean.Token;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by 01370340 on 2017/9/2.
 */

public interface TokenService {

    /**
     * 获取 Token (一般在登录时调用)
     *
     * @param client_id     客户端 id
     * @param client_secret 客户端私钥
     * @param grant_type    授权方式 - 密码
     * @param username      用户名
     * @param password      密码
     * @return Token 实体类
     */
    @POST(Constants.OAUTH.OAUTH_URL)
    @FormUrlEncoded
    Call<Token> getToken(
            @Field("client_id") String client_id, @Field("client_secret") String client_secret,
            @Field("grant_type") String grant_type, @Field("username") String username,
            @Field("password") String password);


    /**
     * 刷新 token
     */
    @POST("https://www.diycode.cc/oauth/token")
    @FormUrlEncoded
    Call<Token> refreshToken(@Field("client_id") String client_id, @Field("client_secret") String client_secret,
                             @Field("grant_type") String grant_type, @Field("refresh_token") String refresh_token);
}

