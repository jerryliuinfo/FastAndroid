package com.apache.fastandroid.home;

import com.apache.fastandroid.bean.CollectBean;
import com.apache.fastandroid.jetpack.StateLiveData;
import com.apache.fastandroid.network.model.Article;
import com.apache.fastandroid.network.model.HomeArticleResponse;
import com.apache.fastandroid.network.response.EmptyResponse;
import com.apache.fastandroid.network.retrofit.ApiEngine;
import com.apache.fastandroid.network.retrofit.BaseResponse;
import com.apache.fastandroid.network.retrofit.ProtocolCallback;
import com.blankj.utilcode.util.ToastUtils;
import com.tesla.framework.support.KidsException;

import java.util.List;

import retrofit2.Call;

/**
 * Created by Jerry on 2021/7/1.
 */
public class HomeReporsitory {
    private static HomeReporsitory reporsitory;

    public static HomeReporsitory newInstance(){
        if (reporsitory == null){
            reporsitory = new HomeReporsitory();
        }
        return reporsitory;
    }

    public void loadTopArticleCo(StateLiveData<List<Article>> stateLiveData){
        Call<BaseResponse<List<Article>>> call = ApiEngine.createApiService().loadTopArticleCo();
        call.enqueue(new ProtocolCallback<List<Article>>(){

            @Override
            public void onSuccess(BaseResponse<List<Article>> protocol) {
                stateLiveData.postSuccess(protocol.getData());
            }

            @Override
            public void onFailure(int fail_code, String msg) {
                stateLiveData.postError(KidsException.newException(fail_code,msg));
            }
        });
    }

    public void loadHomeArticleCo(StateLiveData<HomeArticleResponse> stateLiveData,int pageNum){
        Call<BaseResponse<HomeArticleResponse>> call = ApiEngine.createApiService().loadHomeArticleCo(pageNum);
        call.enqueue(new ProtocolCallback<HomeArticleResponse>(){

            @Override
            public void onSuccess(BaseResponse<HomeArticleResponse> protocol) {
                stateLiveData.postSuccess(protocol.getData());
            }

            @Override
            public void onFailure(int fail_code, String msg) {
                stateLiveData.postError(KidsException.newException(fail_code,msg));
            }
        });
    }

    /**
     * 收藏
     * @param stateLiveData
     * @param id
     */
    public void collect(StateLiveData<CollectBean> stateLiveData, int id){
        Call<BaseResponse<EmptyResponse>> call = ApiEngine.createApiService().collect(id);
        call.enqueue(new ProtocolCallback<EmptyResponse>() {
            @Override
            public void onSuccess(BaseResponse<EmptyResponse> protocol) {
                stateLiveData.postSuccess(new CollectBean(id,true));
            }

            @Override
            public void onFailure(int fail_code, String msg) {
                stateLiveData.postError(KidsException.newException(fail_code,msg));
                ToastUtils.showShort(msg);
            }
        });
    }

    /**
     * 取消收藏
     * @param stateLiveData
     * @param id
     */
    public void uncollect(StateLiveData<CollectBean> stateLiveData,int id){
        Call<BaseResponse<EmptyResponse>> call = ApiEngine.createApiService().unCollect(id);
        call.enqueue(new ProtocolCallback<EmptyResponse>() {
            @Override
            public void onSuccess(BaseResponse<EmptyResponse> protocol) {
                stateLiveData.postSuccess(new CollectBean(id,false));
            }

            @Override
            public void onFailure(int fail_code, String msg) {
                stateLiveData.postError(KidsException.newException(fail_code,msg));
            }
        });
    }
}
