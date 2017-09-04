package com.apache.fastandroid.user.sdk;

import com.apache.fastandroid.artemis.support.bean.UserDetail;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by 01370340 on 2017/9/4.
 */

public interface UserApi {
    /**
     * 获取当前登录者的详细资料
     *
     * @return 用户详情
     */
    @GET("users/me.json")
    Observable<UserDetail> getMe();
}
