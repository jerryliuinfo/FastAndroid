package com.apache.fastandroid.retrofit;

import com.apache.fastandroid.bean.Article;
import com.apache.fastandroid.bean.HomeArticleResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


/**
 * description:
 * author chaojiong.zhang
 * data: 2020/4/24
 * copyright TCL-MIBC
 * 如果是返回值只有code和msg，没有data值的情况， Call 中的参数用 Protocol<BaseResponse>
 */
public interface ApiService {

    @GET("/article/top/json")
    Call<Protocol<List<Article>>> loadTopArticleCo();


    @GET("/article/list/{pageNum}/json")
    Call<Protocol<HomeArticleResponse>> loadHomeArticleCo(@Path("pageNum") int pageNum);



}
