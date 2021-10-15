package com.apache.fastandroid.network.retrofit;


import com.apache.fastandroid.network.model.Article;
import com.apache.fastandroid.network.model.HomeArticleResponse;
import com.apache.fastandroid.network.response.EmptyResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


/**
 * description:
 * author chaojiong.zhang
 * data: 2020/4/24
 * copyright TCL-MIBC
 * 如果是返回值只有code和msg，没有data值的情况， Call 中的参数用 Protocol<BaseResponse>
 */
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


//    @POST("/lg/collect/{id}/json")
//    suspend fun collectCo(@Path("id") id: Int): BaseResponse<EmptyResponse>
//
//    @POST("/lg/uncollect_originId/{id}/json")
//    suspend fun unCollectCo(@Path("id") id: Int): BaseResponse<EmptyResponse>

    @POST("/lg/collect/{id}/json")
    Call<BaseResponse<EmptyResponse>> collect(@Path("id") int id);

    @POST("/lg/uncollect_originId/{id}/json")
    Call<BaseResponse<EmptyResponse>> unCollect(@Path("id") int id);



}
