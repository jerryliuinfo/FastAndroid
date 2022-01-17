package com.apache.fastandroid.network.retrofit;


import com.apache.fastandroid.network.model.Article;
import com.apache.fastandroid.network.model.HomeArticleResponse;
import com.apache.fastandroid.network.response.BaseResponse;
import com.apache.fastandroid.network.response.EmptyResponse;
import com.apache.fastandroid.network.response.TokenInfo;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;



public interface ApiService {

//     置顶数据
    @GET("/article/top/json")
    Call<BaseResponse<List<Article>>> loadTopArticleCo();

    //     置顶数据
    @GET("/article/top/json")
    Observable<BaseResponse<List<Article>>> loadTopArticleCo2();


    //     置顶下面数据
    @GET("/article/list/{pageNum}/json")
    Call<BaseResponse<HomeArticleResponse>> loadHomeArticleCo(@Path("pageNum") int pageNum);

    @GET("/article/list/{pageNum}/json")
    Observable<BaseResponse<HomeArticleResponse>> loadHomeArticleCo2(@Path("pageNum") int pageNum);


    @POST("/lg/collect/{id}/json")
    Call<BaseResponse<EmptyResponse>> collect(@Path("id") int id);

    @POST("/lg/uncollect_originId/{id}/json")
    Call<BaseResponse<EmptyResponse>> unCollect(@Path("id") int id);


    Call<BaseResponse<TokenInfo>> refreshToken(@Path("id") long id);




}
