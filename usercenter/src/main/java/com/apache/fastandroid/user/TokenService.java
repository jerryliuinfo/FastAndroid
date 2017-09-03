package com.apache.fastandroid.user;

import com.apache.fastandroid.user.bean.Token;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by 01370340 on 2017/9/2.
 */

public interface TokenService {
    /**
     * 刷新 token
     */
    @POST("https://www.diycode.cc/oauth/token")
    @FormUrlEncoded
    Call<Token> refreshToken(@Field("client_id") String client_id, @Field("client_secret") String client_secret,
                             @Field("grant_type") String grant_type, @Field("refresh_token") String refresh_token);
}

