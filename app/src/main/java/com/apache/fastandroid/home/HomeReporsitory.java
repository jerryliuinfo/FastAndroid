package com.apache.fastandroid.home;

import com.apache.fastandroid.jetpack.StateLiveData;
import com.apache.fastandroid.retrofit.Protocol;
import com.apache.fastandroid.retrofit.ProtocolCallback;
import com.apache.fastandroid.network.model.Article;
import com.apache.fastandroid.network.model.HomeArticleResponse;
import com.apache.fastandroid.network.retrofit.ApiEngine;
import com.tesla.framework.support.KidsException;

import java.util.List;

import retrofit2.Call;

/**
 * Created by Jerry on 2021/7/1.
 */
public class HomeReporsitory {

    public void loadTopArticleCo(StateLiveData<List<Article>> stateLiveData){
        Call<Protocol<List<Article>>> call = ApiEngine.createApiService().loadTopArticleCo();
        call.enqueue(new ProtocolCallback<List<Article>>(){

            @Override
            public void onSuccess(Protocol<List<Article>> protocol) {
                stateLiveData.postSuccess(protocol.data);
            }

            @Override
            public void onFailure(int fail_code, String msg) {
                stateLiveData.postError(KidsException.newException(fail_code,msg));
            }
        });
    }

    public void loadHomeArticleCo(StateLiveData<HomeArticleResponse> stateLiveData,int pageNum){
        Call<Protocol<HomeArticleResponse>> call = ApiEngine.createApiService().loadHomeArticleCo(pageNum);
        call.enqueue(new ProtocolCallback<HomeArticleResponse>(){

            @Override
            public void onSuccess(Protocol<HomeArticleResponse> protocol) {
                stateLiveData.postSuccess(protocol.data);
            }

            @Override
            public void onFailure(int fail_code, String msg) {
                stateLiveData.postError(KidsException.newException(fail_code,msg));
            }
        });
    }
}
