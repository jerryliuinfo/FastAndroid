package com.apache.fastandroid.novel.support.sdk;

import com.apache.fastandroid.novel.find.bean.Rankings;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by 01370340 on 2017/10/11.
 */

public interface NovelApiService {

    @GET("/ranking")
    Call<Rankings> getRanking(@Query("rankingId") String rankingId);
}
